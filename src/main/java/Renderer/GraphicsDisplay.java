package Renderer;

import core.GameEngine.MBO;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GraphicsDisplay {
    public static final int vertexSize = 4;
    public static final float voxelSize = 1.0f;
    private long window;
    private MBO[] MBOs;
    private final int height;
    private final int width;
    private int frames = 0;
    private double currentTime = 0.0;
    private double lastTime = 0.0;
    private final String name;

    private void printRenderTime(){
        frames++;
        currentTime = glfwGetTime();
        if(currentTime - lastTime > 1.0) {
            System.out.println(frames);
            lastTime = currentTime;
            frames = 0;
        }
    }

    String vertexShaderSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec2 aTex;
            out vec2 texCoord;
                        
            uniform mat4 camMatrix;
                        
            void main()
            {
                gl_Position = camMatrix * vec4(aPos, 1.0f);
                texCoord = aTex;
            }
            """;

    String fragmentShaderSource = """
            #version 330 core
            out vec4 FragColor;
            in vec2 texCoord;
            uniform sampler2D tex0;
            void main()
            {
                FragColor = texture(tex0, texCoord);
            }
            """;

    public GraphicsDisplay(int width, int height, String name, MBO[] MBOs) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.MBOs = MBOs;
    }

    public void run() {
        System.out.println("GraphicsDisplay has launched with LWJGL " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ((key == GLFW_KEY_ESCAPE || key == GLFW_KEY_Q) && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private int countVoxels (MBO[] mboS) {
        int sum = 0;
        for (MBO mbo : mboS) sum += mbo.voxels.length;
        return sum;
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        glEnable(GL_DEPTH_TEST);

        int mboSLength = countVoxels(MBOs);
        float[] vertices = new float[mboSLength * 120];
        int[] indices = new int[mboSLength * 36];

        int idVoxel = 0;
        for (MBO mbo : MBOs) {
            for (int k = 0; k < mbo.voxels.length; k++) {
                float[] verticesTemp = {
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 0.0f,

                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 0.0f,

                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 0.0f,

                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 0.0f,

                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 0.0f,

                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 1.0f, 0.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 1.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize - voxelSize, 0.0f, 1.0f,
                        mbo.getX() + mbo.voxels[k].getX() * voxelSize + voxelSize, mbo.getY() + mbo.voxels[k].getY() * voxelSize + voxelSize, mbo.getZ() + mbo.voxels[k].getZ() * voxelSize, 0.0f, 0.0f,

                };

                int[] indicesTemp = {
                        0, 2, 1,
                        0, 3, 2,

                        4, 6, 5,
                        4, 7, 6,

                        8, 10, 9,
                        8, 11, 10,

                        12, 14, 13,
                        12, 15, 14,

                        16, 18, 17,
                        16, 19, 18,

                        20, 22, 21,
                        20, 23, 22
                };

                for (int j = 0; j < 120; j++) {
                    vertices[idVoxel * 120 + j] = verticesTemp[j];
                }
                for (int j = 0; j < 36; j++) {
                    indices[idVoxel * 36 + j] = indicesTemp[j] + idVoxel * 24;
                }
                idVoxel++;
            }
        }



        Shader shader = new Shader(vertexShaderSource, fragmentShaderSource);

        VAO vertexArrayObject = new VAO();
        vertexArrayObject.bind();

        VBO vertexBufferObject = new VBO(vertices);
        EBO elementBufferObject = new EBO(indices);

        vertexArrayObject.LinkAttrib(vertexBufferObject, 0, 20, 0);
        vertexArrayObject.LinkAttrib(vertexBufferObject, 1, 20, 12);
        vertexArrayObject.unbind();
        vertexBufferObject.unbind();
        elementBufferObject.unbind();

        Texture texture = new Texture("picture.png");
        texture.texUnit(shader, "tex0", 0);

        Camera camera = new Camera(width, height, new Vector3f(0.0f, 0.0f, 2.0f));

        float rotation = 0.0f;
        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            shader.activate();

            camera.Inputs(window);
            camera.Matrix(45.0f, 0.1f, 10000.0f, shader, "camMatrix");

            texture.bind();
            vertexArrayObject.bind();
            printRenderTime();
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        vertexArrayObject.delete();
        vertexBufferObject.delete();
        elementBufferObject.delete();
        texture.delete();
        shader.delete();
    }

}
