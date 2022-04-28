import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.Launcher;
import org.junit.jupiter.api.Test;

public class GraphicTests {

    @Test
    void newTest() {
        MBO[] mbos = {new MBO(MBO.genVoxelArray(2), new Vector3D(0.0f, 1.0f, 0.0f),new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f)};

        Launcher launcher = new Launcher(mbos, 100L);
        launcher.run();
    }

    @Test
    void autoTest() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(5.0f, 10.0f, 0.0f), new RigidBody(300.0f, 20.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(12.0f, 60.0f, 0.0f),new RigidBody(10.0f, -40.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(15.0f, 20.0f, 0.0f),new RigidBody(50.0f, 0.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(-6.0f, -10.0f, 0.0f),new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(20), new Vector3D(12.0f, -10.0f, 0.0f),new RigidBody(100.0f, 20.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(2), new Vector3D(-10.0f, -10.0f, 0.0f),new RigidBody(1.0f, 2.0f, 0.0f, true), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(4), new Vector3D(8.0f, -10.0f, 0.0f), new RigidBody(1.0f, 2.0f, 0.0f, true),0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos, 30L);
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
