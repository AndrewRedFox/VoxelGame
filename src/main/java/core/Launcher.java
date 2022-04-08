package core;

import Renderer.GraphicsDisplay;
import com.sun.jna.platform.win32.WinDef;
import core.GameEngine.MBO;
import core.GameEngine.Voxel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class Launcher {
    GraphicsDisplay graphicsDisplay;

    Voxel[] genVoxelArray(int count){
        Voxel[] mas = new Voxel[count];
        int[] cord = {0, 0, 0};
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < count; i++){
            mas[i] = new Voxel(cord[0], cord[1], cord[2]);
            set.add(cord[0] * 100000000 + cord[1] * 10000 + cord[2]);
            System.out.println(cord[0] + " " + cord[1] + " " + cord[2]);

            while (set.contains(cord[0] * 100000000 + cord[1] * 10000 + cord[2])) {
                cord[new Random().nextInt(3)]+= (new Random().nextBoolean()?1:-1);
            }
        }
        return mas;
    }

    Launcher() {
        MBO[] mas = {
                new MBO(genVoxelArray(10000), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                new MBO(genVoxelArray(10000), 100.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                new MBO(genVoxelArray(10000), 0.0f, 100.0f, 0.0f, 0.0f, 0.0f, 0.0f),
                new MBO(genVoxelArray(10000), 100.0f, 100.0f, 0.0f, 0.0f, 0.0f, 0.0f)
                //new MBO(genVoxelArray(1000), 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
        };
        graphicsDisplay = new GraphicsDisplay(800, 800, "VoxelGame", mas);
    }

    public void run() {
        graphicsDisplay.run();
    }

}
