package core;

import Renderer.Loader;
import Renderer.RawModel;
import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;
import input.Keyboard;
import input.Mouse;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class Launcher {
    Loader loader;
    GraphicsDisplay graphicsDisplay;
    WinDef.DWORD milliseconds;


    Launcher() {
        graphicsDisplay = new GraphicsDisplay(1280, 720, "VoxelGame");
        loader = new Loader();
        graphicsDisplay.init();
    }

    public void run() {
        graphicsDisplay.run();
    }

}
