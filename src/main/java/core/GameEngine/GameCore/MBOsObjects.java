package core.GameEngine.GameCore;

import core.GameEngine.MBO;
import core.GameEngine.Voxel;

import java.util.HashSet;
import java.util.Random;

public class MBOsObjects {
    private MBO[] objects;

    public MBOsObjects(MBO[] mbos) {
        this.objects = mbos;
    }

    private static Voxel[] genVoxelArray(int count) {
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

    public int size(){
        return objects.length;
    }

    public MBO getByIndex(int i) {
        return objects[i];
    }
}
