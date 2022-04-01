package core;
import Renderer.Loader;
import Renderer.RawModel;
import Renderer.GraphicsDisplay;
import input.Keyboard;
import input.Mouse;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;

public class Launcher {
    Loader loader;
    GraphicsDisplay graphicsDisplay;

    Launcher(){
        graphicsDisplay = new GraphicsDisplay(1280, 720, "VoxelGame");
        loader = new Loader();
        graphicsDisplay.init();
    }

    public void run(){
        graphicsDisplay.run();
    }

}
