package core.GameEngine;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.Launcher;

import java.util.HashSet;
import java.util.Random;

public class PhysicsCore {
    public MBOsObjects mbOsObjects;
    public SimulationSimple simulate;//object
    private Launcher launcher;
    private long delay;

    public PhysicsCore(MBO[] mbos, long delay, Launcher launcher) {
        this.mbOsObjects = new MBOsObjects(mbos);
        this.delay = delay;
        this.launcher = launcher;
        this.simulate = new SimulationSimple(2.0f, mbOsObjects.getByIndex(0), mbOsObjects);
    }

    public void run() {
        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!launcher.toClose()) {
                synchronized (this) {
                    MBO temp = mbOsObjects.getByIndex(0);
                    simulate.operate(temp);
                    temp.setVector3D(simulate.getObject());
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            System.out.println("End runSim");
        });
        thread.start();
    }

}
