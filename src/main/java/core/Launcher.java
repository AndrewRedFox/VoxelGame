package core;
import Renderer.Renderer;
import Renderer.Loader;
import Renderer.RawModel;
import core.WindowManager;
import input.Keyboard;
import input.Mouse;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;

public class Launcher {
    private WindowManager manager;
    Loader loader = new Loader();
    Renderer renderer = new Renderer();
    //staticShader shader = new staticShader();

    public void run() {
        init();
    }

    private void init() {
        this.manager = new WindowManager(1280, 720, "VoxelGame");
        manager.init();
        update();
    }

    private void update() {
        float[] v_positions =
                {
                        0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f,//1 и 2 вершина
                        -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f//3 и 4 вершинаd

                };
        int[] indexies = {0, 1, 2, 0, 2, 3};
        RawModel model = loader.loadToVao(v_positions,indexies);
        renderer.render(model);

        while (!GLFW.glfwWindowShouldClose(WindowManager.getWindow().getThisWindow())) {
            if (Keyboard.keyPressed(GLFW.GLFW_KEY_A)) {
                System.out.println("Клавиша А нажата");
            }
            if (Mouse.buttonPressed(GLFW_MOUSE_BUTTON_1)) {
                System.out.println("ЛКМ нажата");
            }
            //System.out.println(mouse.getMouseX() + " " + mouse.getMouseY());
            Keyboard.handleKeyboardInput();//обработчик нажатий клавиатуры
            Mouse.handleMouseInput();//обработчик нажатий мыши

            renderer.prepare();
            renderer.render(model);
            //shader.start();
            //renderer.render(model);
            //shader.stop();


            manager.loop();
            //rendering
        }
        //shader.cleanUp();
        loader.cleanUp();
        glfwDestroyWindow(WindowManager.getWindow().getThisWindow());
    }
    public WindowManager getManager() {
        return manager;
    }
}
