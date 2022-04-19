package core.GameEngine;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.Launcher;

import java.util.HashSet;
import java.util.Random;

public class PhysicsCore {
    public MBO[] mbos;
    public SimulationSimple simulate = new SimulationSimple(2.0f);//object
    private Launcher launcher;
    private long delay;

    public PhysicsCore(MBO[] mbos, long delay, Launcher launcher) {
        this.mbos = mbos;
        this.delay = delay;
        this.launcher = launcher;
    }

    public void run() {
        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!launcher.toClose()) {
                synchronized (this) {
                    simulate.setObject(mbos[0]);//одновременно идет проверка коллизий и применение силы на обьект
                    simulate.operate(mbos[0]);
                    mbos[0].setVector3D(simulate.getObject());
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
