package Renderer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import static org.lwjgl.opengl.GL33.*;

public class Skybox {
    private static final String[] imgResources = {
            "src/main/resources/right.jpg",
            "src/main/resources/left.jpg",
            "src/main/resources/top.jpg",
            "src/main/resources/bottom.jpg",
            "src/main/resources/front.jpg",
            "src/main/resources/back.jpg"
    };
    public static final int height = 2048, width = 2048;
    private static final Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final float pi = 3.14159265359f;

    int cubemapTexture;

    int skyboxVAO, skyboxVBO, skyboxEBO;

    float[] vertices = new float[]{
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
    };
    int[] indices = new int[]{
            1, 2, 6,
            6, 5, 1,

            0, 4, 7,
            7, 3, 0,

            4, 5, 6,
            6, 7, 4,

            0, 3, 2,
            2, 1, 0,

            0, 1, 5,
            5, 4, 0,

            3, 7, 6,
            6, 2, 3
    };

    //UniversalContainer universalContainer = new UniversalContainer(vertices, indices);


    void init() {
        skyboxVAO = glGenVertexArrays();
        skyboxVBO = glGenBuffers();
        skyboxEBO = glGenBuffers();
        glBindVertexArray(skyboxVAO);
        glBindBuffer(GL_ARRAY_BUFFER, skyboxVBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, skyboxEBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 12, 0);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        cubemapTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        for (int i = 0; i < 6; i++) {
            final String path = imgResources[i];
            try {

                File imgPath = new File(path);
                BufferedImage image = ImageIO.read(imgPath);

                int[] pixels = new int[image.getWidth() * image.getHeight()];
                image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
                ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

                for (int h = 0; h < image.getHeight(); h++) {
                    for (int w = 0; w < image.getWidth(); w++) {
                        int pixel = pixels[h * image.getWidth() + w];

                        buffer.put((byte) ((pixel >> 16) & 0xFF));
                        buffer.put((byte) ((pixel >> 8) & 0xFF));
                        buffer.put((byte) (pixel & 0xFF));
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                    }
                }
                buffer.flip();
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
                System.out.println("Texture at path ' " + path + " ' (capacity = " + buffer.capacity() + ") loaded");
            } catch (Exception e) {
                System.out.println("Unable to load " + path + " texture");
            }
        }

    }

    void run(float FOVdeg, float nearPlane, float farPlane, Camera camera, Shader shader){
        shader.activate();
        Vector3f position = new Vector3f(camera.position), orientation = new Vector3f(camera.orientation);
        Matrix4f view = new Matrix4f();
        Vector3f center = new Vector3f().add(position).add(orientation);
        view = view.lookAt(position, center, up);
        Matrix4f proj = new Matrix4f();
        proj = proj.perspective(FOVdeg / 180.0f * pi, (float) (width / height), nearPlane, farPlane);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "view"), false, view.get(stack.mallocFloat(16)));
        } catch (Exception e) {
            System.out.println(e + " error in Skybox: can't correctly send uniform data");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "projection"), false, proj.get(stack.mallocFloat(16)));
        } catch (Exception e) {
            System.out.println(e + " error in Skybox: can't correctly send uniform data");
        }

        glBindVertexArray(skyboxVAO);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
