package core;

import Renderer.GraphicsDisplay;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;

public class Launcher {
    private final GraphicsDisplay graphicsDisplay;
    private final PhysicsCore physicsCore;

    public Launcher(MBO[] mbos, long delay) {
        this.physicsCore = new PhysicsCore(mbos, delay, this);
        this.graphicsDisplay = new GraphicsDisplay(1080, 720, "VoxelGame", physicsCore, this);
    }


    public boolean toClose() {
        return graphicsDisplay.toClose;
    }

    public void run() {
        physicsCore.run();
        physicsCore.runCharacter();
        graphicsDisplay.run();
    }

}



