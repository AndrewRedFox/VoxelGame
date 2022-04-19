package core.GameEngine.GameCore;

import core.GameEngine.MBO;
import core.GameEngine.Voxel;

import java.util.HashSet;
import java.util.Random;

public class MBOsObjects {
    public static MBO object = new MBO(genVoxelArray(1), new Vector3D(2.0f, 50.0f, 0.0f), 0.0f, 0.0f, 0.0f);
    public static MBO[] mas = new MBO[]{
            new MBO(genVoxelArray(10), new Vector3D(0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f),
            object
    };

    public MBOsObjects(){

    }

    static Voxel[] genVoxelArray(int count) {
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

    public static MBO[] getMBOs(){
        return mas;
    }
}
