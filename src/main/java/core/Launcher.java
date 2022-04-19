package core;

import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;
import core.GameEngine.Voxel;

import java.util.HashSet;
import java.util.Random;


public class Launcher {
    private final GraphicsDisplay graphicsDisplay;
    private long delay = -1L;
    public MBO object;
    public SimulationSimple simulate = new SimulationSimple(2.0f, object);
    public MBO[] mbos;
    //GraphicsDisplay graphicsDisplay;
    public PhysicsCore physicsCore = new PhysicsCore();

   /* public Launcher() {
        graphicsDisplay = new GraphicsDisplay(800, 800, "VoxelGame", physicsCore.getMas());
        physicsCore.setGraphicsDisplay(graphicsDisplay);
    }*/

    Launcher() {
        /*mbos = new MBO[]{
                object,
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 1.0f, 0.0f), 0.0f, 0.0f, 0.0f),
        };
        object = new MBO(MBO.genVoxelArray(2), new Vector3D(0.0f, 0.0f, -10.0f), 0.0f, 0.0f, 0.0f);*/
        graphicsDisplay = new GraphicsDisplay(1080, 1080, "VoxelGame", this);
        delay = 1000l;
        physicsCore.setGraphicsDisplay(graphicsDisplay);
    }

    /*public Launcher(MBO[] mbos, MBO object, long delay) {
        this.mbos = mbos;
        this.object = object;
        this.graphicsDisplay = new GraphicsDisplay(1080, 1080, "VoxelGame", this);
        this.delay = delay;
    }

    public Launcher(MBO[] mbos) {
        this.mbos = mbos;
        this.graphicsDisplay = new GraphicsDisplay(1080, 1080, "VoxelGame", this);
    }*/

    public boolean toClose() {
        return graphicsDisplay.toClose;
    }

    public GraphicsDisplay getGraphicsDisplay() {
        return graphicsDisplay;
    }


    public void run() {
        //runSimulation();
        runGUI();
        physicsCore.run();
    }

    public void runGUI() {
        System.out.println("start gui");
        graphicsDisplay.run();
        System.out.println("close gui");
    }

    /*public void runSimulation() {
        if (object == null) throw new IllegalStateException("object is null");
        if (delay < 1) throw new IllegalStateException("incorrect delay");
        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!graphicsDisplay.toClose) {
                simulate.setObject(object);
                simulate.operate();
                object.setVector3D(simulate.getObject());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("End runSim");
        });
        thread.start();
    }*/

    /*public void run() {
        physicsCore.run();
    }*/

}



