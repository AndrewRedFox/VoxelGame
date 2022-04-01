package core;

import input.Mouse;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.system.MemoryStack.*;


public class WindowManager {
    private long window;
    private int width;
    private int hight;
    private String title;
    private static WindowManager instance;

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return hight;
    }

    public WindowManager(int weight, int hight, String name) {
        this.width = weight;
        this.hight = hight;
        this.title = name;
        instance = this;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, hight, title, 0, 0);

        if (window == 0)
            throw new RuntimeException("Failed to create the GLFW window");

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
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();

        //mouse.setMouseCallbacks(window);
        glfwSetCursorPos(window,width/2,hight/2);//курсор в центре экрана

    }

    public void loop() {

        glfwSwapBuffers(window);
        glfwPollEvents();

    }

    public static WindowManager getWindow() {
        return instance;
    }

    public long getThisWindow(){
        return window;
    }
}
