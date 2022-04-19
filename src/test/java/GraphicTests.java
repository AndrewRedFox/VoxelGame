import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.Voxel;
import core.Launcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class GraphicTests {
    @Test
    void autoTest() {
        MBO object = new MBO(
                MBO.genVoxelArray(2),
                new Vector3D(0.0f, 0.0f, -10.0f), 0.0f, 0.0f, 0.0f
        );
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(2), new Vector3D(0.0f, 1.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                object
        };
        Launcher launcher = new Launcher(mbos, object, 100l);
        launcher.run();
    }

    @Test
    void maxGUITest() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 400.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, -400.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(400.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(-400.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 0.0f, 400.0f), 0.0f, 0.0f, 0.0f),
                new MBO(MBO.genVoxelArray(10000), new Vector3D(0.0f, 0.0f, -400.0f), 0.0f, 0.0f, 0.0f)
        };
        Launcher launcher = new Launcher(mbos);
        launcher.runGUI();
    }

    @Test
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
    }
}
