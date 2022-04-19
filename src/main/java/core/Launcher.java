package core;

import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;
import core.GameEngine.Voxel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class Launcher {
    GraphicsDisplay graphicsDisplay;
    PhysicsCore physicsCore = new PhysicsCore();

    public Launcher() {
        graphicsDisplay = new GraphicsDisplay(800, 800, "VoxelGame", physicsCore.getMas());
        physicsCore.setGraphicsDisplay(graphicsDisplay);
    }

    public GraphicsDisplay getGraphicsDisplay(){
        return graphicsDisplay;
    }

    public void run() {
        physicsCore.run();
    }

}
