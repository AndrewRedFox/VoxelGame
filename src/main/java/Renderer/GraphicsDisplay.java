package Renderer;

import com.sun.jna.platform.win32.WinDef;
import core.GameEngine.GameCore.*;
import input.Keyboard;
import input.Mouse;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;


public class GraphicsDisplay {
    private long window;
    private int width;
    private int height;
    private String title;
    private static GraphicsDisplay instance;

    public GraphicsDisplay(int weight, int height, String name) {
        this.width = weight;
        this.height = height;
        this.title = name;
        instance = this;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());//разрешение основного монитора

            // Center the window
            glfwSetWindowPos(window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();

        //mouse.setMouseCallbacks(window);
        glfwSetCursorPos(window, width / 2.0, height / 2.0);//курсор в центре экрана

    }

    public float timeElapsed;
    public ConstantVelocity velocity = new ConstantVelocity();
    public MotionUnderGravitation motionUnderGravitation = new MotionUnderGravitation(new Vector3D(0.0f, -9.81f, 0.0f));
    public MassConnectedWithSpring massConnectedWithSpring = new MassConnectedWithSpring(0.8f);

    public void run() {
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        while (!GLFW.glfwWindowShouldClose(window)) {
            Keyboard.handleKeyboardInput();//обработчик нажатий клавиатуры
            Mouse.handleMouseInput();//обработчик нажатий мыши

            ///////////////
            float dt = 60.0f / 1000.0f;
            timeElapsed += dt;

            float maxPossible_dt = 0.1f;
            int numOfIterations = (int) (dt / maxPossible_dt) + 1;
            if (numOfIterations != 0) {
                dt = dt / numOfIterations;
            }
            for (int i = 0; i < numOfIterations; ++i) {
                //velocity.operate(dt);
                //motionUnderGravitation.operate(dt);
                // massConnectedWithSpring.operate(dt);
            }


            ////////////////
            renderFrame();
        }
    }

    public void renderFrame() {
        glClear(GL_COLOR_BUFFER_BIT);
        glColor3d(1.0f, 0.0f, 1.0f);
        glBegin(GL_TRIANGLES);
        glVertex2f(1.0f, 1.0f);
        glVertex2f(1.0f, 0.0f);
        glVertex2f(0.0f, 1.0f);
        glEnd();
        glfwSwapBuffers(window);
        glfwPollEvents();

    }

    public static GraphicsDisplay getWindow() {
        return instance;
    }

    public long getThisWindow() {
        return window;
    }
}
