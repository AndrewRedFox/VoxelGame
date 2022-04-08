package core;

import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;


public class Launcher {
    GraphicsDisplay graphicsDisplay;

    Launcher() {
        graphicsDisplay = new GraphicsDisplay(800, 800, "VoxelGame");
    }

    public void run() {
        graphicsDisplay.run();
    }

}
