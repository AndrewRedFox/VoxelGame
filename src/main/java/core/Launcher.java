package core;

import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.Voxel;

import java.util.HashSet;
import java.util.Random;


public class Launcher {
    GraphicsDisplay graphicsDisplay;
    public MBO object = new MBO(genVoxelArray(2), new Vector3D(0.0f, 0.0f, -10.0f), 0.0f, 0.0f, 0.0f);
    public SimulationSimple simulate = new SimulationSimple(2.0f, object);
    public MBO[] mbos;

    Voxel[] genVoxelArray(int count) {
        Voxel[] mas = new Voxel[count];
        int[] cord = {0, 0, 0};
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            mas[i] = new Voxel(cord[0], cord[1], cord[2]);
            set.add(cord[0] * 100000000 + cord[1] * 10000 + cord[2]);
            //System.out.println(cord[0] + " " + cord[1] + " " + cord[2]);

            while (set.contains(cord[0] * 100000000 + cord[1] * 10000 + cord[2])) {
                cord[new Random().nextInt(3)] += (new Random().nextBoolean() ? 1 : -1);
            }
        }
        return mas;
    }

    Launcher() {
        mbos = new MBO[]{
                object,
                //new MBO(genVoxelArray(10), new Vector3D(0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                //new MBO(new Voxel[]{ new Voxel(0, 0, 0), new Voxel(0, 0, 1)} , new Vector3D(1.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(genVoxelArray(50000), new Vector3D(0.0f, 1.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                //new MBO(genVoxelArray(1), new Vector3D(0.0f, 0.0f, -1.0f), 0.0f, 0.0f, 0.0f)
        };

        graphicsDisplay = new GraphicsDisplay(1080, 1080, "VoxelGame", this);
    }

    public void run() {
        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!graphicsDisplay.toClose) {
                simulate.setObject(object);
                simulate.operate();
                object.setVector3D(simulate.getObject());
                //System.out.println(object.getX() + " " + object.getY() + " " + object.getZ());
                try {
                    Thread.sleep(1000L);
                    synchronized (this) {
                        /*if(Math.random() > 0.90) {
                            mbos = new MBO[(int) (Math.random() * 200) + 6000 + 1];
                        }
                        mbos[0] = object;
                        for (int i = 1; i < mbos.length; i++) {
                            if(Math.random() > 0.99 || mbos[i] == null) {
                                mbos[i] = new MBO(genVoxelArray(5), new Vector3D((int) (Math.random() * 640), (int) (Math.random() * 640), (int) (Math.random() * 640)), 0.0f, 0.0f, 0.0f);
                            }
                        }
                        for (int j = 0; j < mbos.length; j++) {
                            if (mbos[j] == null) {
                                System.out.println(1);
                            }
                        }*/
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                //System.out.println(graphicsDisplay.toClose);
            }
            System.out.println("End runSim");
        });
        thread.start();
        System.out.println("start gui");
        graphicsDisplay.run();
        System.out.println("close gui");
    }

}
