package Renderer;
import core.GameEngine.GameCore;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.*;
import java.nio.file.Files;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GraphicsDisplay {
    private long window;
    private final int height;
    private final int width;
    private final String name;
    private GameCore gameCore;

    String vertexShaderSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec2 aTex;
            out vec2 texCoord;
            void main()
            {
                gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
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

    public GraphicsDisplay(int width, int height, String name, GameCore gameCore){
        this.height = height;
        this.width = width;
        this.gameCore = gameCore;
        this.name = name;
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
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
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

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        float[] vertices = {
                -0.5f, -0.5f,  0.0f,    0.0f, 0.0f,
                -0.5f,  0.5f,  0.0f,    0.0f, 1.0f,
                 0.5f,  0.5f,  0.0f,    1.0f, 1.0f,
                 0.5f, -0.5f,  0.0f,    1.0f, 0.0f
        };

        int[] indices = {
                0, 2, 1,
                0, 3, 2
        };

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

        while ( !glfwWindowShouldClose(window) ) {
            glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            shader.activate();
            texture.bind();
            vertexArrayObject.bind();

            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

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
