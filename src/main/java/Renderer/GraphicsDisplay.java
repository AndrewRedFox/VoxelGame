package Renderer;

import core.GameEngine.PhysicsCore;
import core.Launcher;
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
    public static final float voxelSize = 2.0f;
    private long window;
    private final int height;
    private final int width;
    private int frames = 0;
    private double currentTime = 0.0;
    public boolean toClose = false;
    private double lastTime = 0.0;
    private final String name;
    private Launcher launcher;
    private PhysicsCore physicsCore;


    private void printRenderTime() {
        frames++;
        currentTime = glfwGetTime();
        if (currentTime - lastTime > 1.0) {
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

    public GraphicsDisplay(int width, int height, String name, PhysicsCore physicsCore, Launcher launcher) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.launcher = launcher;
        this.physicsCore = physicsCore;
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
            if ((key == GLFW_KEY_ESCAPE || key == GLFW_KEY_Q) && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
                toClose = true;
            }
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

    private void loop() {
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        Shader shader = new Shader(vertexShaderSource, fragmentShaderSource);

        Texture texture = new Texture("picture3.png", 1024, 512);
        texture.texUnit(shader, "tex0", 0);

        Camera camera = new Camera(width, height, new Vector3f(0.0f, 0.0f, 2.0f), this);

        ArrayContainer arrayContainer = new ArrayContainer(physicsCore);

        arrayContainer.refreshMBOS();

        VAO vertexArrayObject = new VAO();
        vertexArrayObject.bind();
        VBO vertexBufferObject = new VBO(arrayContainer);
        EBO elementBufferObject = new EBO(arrayContainer);

        vertexBufferObject.bindRefresh();
        elementBufferObject.bindRefresh();

        vertexArrayObject.LinkAttrib(vertexBufferObject, 0, 20, 0);
        vertexArrayObject.LinkAttrib(vertexBufferObject, 1, 20, 12);

        vertexArrayObject.unbind();
        vertexBufferObject.unbind();
        elementBufferObject.unbind();

        while (!glfwWindowShouldClose(window)) {
            //arrayContainer.refreshMBOS();

            vertexArrayObject.bind();
            vertexBufferObject.bindRefresh();
            elementBufferObject.bindRefresh();

            vertexArrayObject.LinkAttrib(vertexBufferObject, 0, 20, 0);
            vertexArrayObject.LinkAttrib(vertexBufferObject, 1, 20, 12);

            vertexArrayObject.unbind();
            vertexBufferObject.unbind();
            elementBufferObject.unbind();


            glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            shader.activate();

            camera.Inputs(window);
            camera.Matrix(45.0f, 0.1f, 10000.0f, shader, "camMatrix");

            texture.bind();
            vertexArrayObject.bind();

            printRenderTime();
            glDrawElements(GL_TRIANGLES, arrayContainer.indices.length, GL_UNSIGNED_INT, 0);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        vertexArrayObject.delete();
        vertexBufferObject.delete();
        elementBufferObject.delete();
        texture.delete();
        shader.delete();
        toClose = true;
    }

}
