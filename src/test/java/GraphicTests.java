import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.Voxel;
import core.Launcher;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class GraphicTests {

    @Test
    void newTest() {
        MBO[] mbos = {new MBO(MBO.genVoxelArray(2), new Vector3D(0.0f, 1.0f, 0.0f), new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f)};

        Launcher launcher = new Launcher(mbos, 100L);
        launcher.run();
    }

    @Test
    void angleTest() {
        MBO[] mbos = {new MBO(MBO.genVoxelArray(10), new Vector3D(0.0f, 0.0f, -10.0f), new RigidBody(1.0f, 2.0f, 0.0f, false), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10), new Vector3D(10.0f, 0.0f, -10.0f), new RigidBody(0.0f, new Vector3D(0, 0, 0), 0.0f, false), 0.0f, 0.0f, 0.0f)};

        Launcher launcher = new Launcher(mbos, 100L);
        launcher.run();
    }

    @Test
    void autoTest() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(-10.0f, 5.0f, 0.0f), new RigidBody(150.0f, new Vector3D(10, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(2), new Vector3D(10.0f, 5.0f, 0.0f), new RigidBody(15.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(2), new Vector3D(5.0f, 60.0f, 0.0f), new RigidBody(300.0f, 20.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(12.0f, 60.0f, 0.0f), new RigidBody(10.0f, -40.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(15.0f, 60.0f, 0.0f), new RigidBody(50.0f, 0.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(-6.0f, -10.0f, 0.0f), new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(20), new Vector3D(12.0f, -10.0f, 0.0f), new RigidBody(100.0f, 20.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(-10.0f, -10.0f, 0.0f), new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(4), new Vector3D(8.0f, -10.0f, 0.0f), new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest1() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(-10.0f, 5.0f, 0.0f), new RigidBody(15.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(2), new Vector3D(10.0f, 5.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(1), new Vector3D(0.0f, -20.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(1), new Vector3D(2.0f, -20.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(1), new Vector3D(4.0f, -20.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(1), new Vector3D(6.0f, -20.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(1), new Vector3D(8.0f, -20.0f, 0.0f), new RigidBody(150.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
        };
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest2() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(-10.0f, 25.0f, 0.0f), new RigidBody(17.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(100.0f, 25.0f, 0.0f), new RigidBody(15.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(4), new Vector3D(160.0f, 5.0f, 0.0f), new RigidBody(5.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//x-20
                new MBO(MBO.genVoxelArray(6), new Vector3D(2.0f, 5.0f, 0.0f), new RigidBody(5.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(40), new Vector3D(4.0f, -10.0f, 0.0f), new RigidBody(50.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest3() {
        int n = 10000;
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(n/2), new Vector3D(-n/20.0f, 8.0f, 0.0f), new RigidBody(15.0f, new Vector3D(1, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(n/2), new Vector3D(n/20.0f, 5.0f, 0.0f), new RigidBody(15.0f, new Vector3D(-1, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)//*****
        };
        Launcher launcher = new Launcher(mbos, 10L);
        launcher.run();
    }

    @Test
    void autoTest4() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(5), new Vector3D(14.0f, 2.0f, 0.0f), new RigidBody(15.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(1), new Vector3D(10.0f, 4.0f, 0.0f), new RigidBody(8.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)//*****
        };
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest4_5() {
        int n = 100;
        Random random = new Random();
        MBO[] mbos = new MBO[n];
        for (int i = 0; i < n; i++) {
            mbos[i] = new MBO(
                    MBO.genVoxelArray(random.nextInt(8) + 2),
                    new Vector3D(
                            20.0f * random.nextInt(10),
                            20.0f * random.nextInt(10),
                            20.0f * random.nextInt(10)
                    ),
                    new RigidBody(
                            random.nextFloat(),
                            new Vector3D(40 * random.nextFloat(),
                                    40 * random.nextFloat(),
                                    40 * random.nextFloat()
                            ),
                            random.nextFloat(), true),
                    0.0f, 0.0f, 0.0f);
        }
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest5() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(5.0f, 0.0f, 0.0f), new RigidBody(15.0f, new Vector3D(0, -25, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(2), new Vector3D(5.0f, 0.0f, 0.0f), new RigidBody(20.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)//*****
        };
        Launcher launcher = new Launcher(mbos, 30L);
        launcher.run();
    }

    @Test
    void autoTest6() {//??????!!!!!!!!!!!!!!!!!!!!!
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(10.0f, 20.0f, 0.0f), new RigidBody(5.0f, new Vector3D(0, -25, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),//*****
                new MBO(MBO.genVoxelArray(2), new Vector3D(10.0f, -15.0f, 0.0f), new RigidBody(20.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)//*****
        };
        Launcher launcher = new Launcher(mbos, 10L);
        launcher.run();
    }

    @Test
    void autoTest7Ship() {
        MBO[] mbos = {
                new MBO(MBO.genShip(), new Vector3D(10.0f, 2.0f, 0.0f), new RigidBody(5.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(100.0f, -15.0f, 0.0f), new RigidBody(20.0f, new Vector3D(0, 0, 0), 0.0f, true), 0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos, 10L);
        launcher.run();
    }

    /*@Test
    void maxGUITest() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 400.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, -400.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(400.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(-400.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 0.0f, 400.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 0.0f, -400.0f), 0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos, 100L);
        launcher.run();
    }*/

    /*@Test
    void cubeGUITest() {
        int n = 48;
        Voxel[] voxels = new Voxel[n * n * n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < n; k++) {
                    voxels[i * n * n + j * n + k] = new Voxel(i, j, k);
                }
            }
        }
        MBO[] mbos = new MBO[]{ new MBO(voxels, new Vector3D(0.0f, 0.0f, -100.0f), 0.0f, 0.0f, 0.0f)};
        Launcher launcher = new Launcher(mbos);
        launcher.runGUI();
    }

    @Test
    void randomSpawnTestWithObject() {
        //MBO[] mbos = {};
        MBO object = new MBO(
                MBO.genVoxelArray(2),
                new Vector3D(0.0f, 0.0f, -10.0f), 0.0f, 0.0f, 0.0f
        );
        Launcher launcher = new Launcher(new MBO[]{object}, object, 100L);
        Thread thread = new Thread(() -> {
            while (!launcher.toClose()) {
                synchronized (launcher) {
                    if (Math.random() > 0.90) {
                        launcher.mbos = new MBO[(int) (Math.random() * 200) + 6000 + 1];
                    }
                    launcher.mbos[0] = object;
                    for (int i = 1; i < launcher.mbos.length; i++) {
                        if (Math.random() > 0.99 || launcher.mbos[i] == null) {
                            launcher.mbos[i] = new MBO(MBO.genVoxelArray(5), new Vector3D((int) (Math.random() * 640), (int) (Math.random() * 640), (int) (Math.random() * 640)), 0.0f, 0.0f, 0.0f);
                        }
                    }
                }
                try {
                    Thread.sleep(250L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        );
        thread.start();
        launcher.run();
    }*/
}
