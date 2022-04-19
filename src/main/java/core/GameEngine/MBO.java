package core.GameEngine;

import core.GameEngine.GameCore.Vector3D;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MBO {
    public Voxel[] voxels;
    private Vector3D vector3D = new Vector3D();//*
    private float angleX, angleY, angleZ;

    public MBO(Voxel[] voxels, Vector3D vector3D, float angleX, float angleY, float angleZ) {
        this.voxels = voxels;
        this.vector3D = vector3D;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;

        HashSet<Long> set = new HashSet<>();
        long size = voxels.length + 10;
        for (Voxel voxel : this.voxels) {
            set.add((voxel.getX() * size + voxel.getY()) * size + voxel.getZ());
        }
        for (Voxel voxel : this.voxels) {
            voxel.renderSpec[1] = !set.contains(voxel.getLongWithAdj(1, 0, 0, size));
            voxel.renderSpec[2] = !set.contains(voxel.getLongWithAdj(-1, 0, 0, size));
            voxel.renderSpec[4] = !set.contains(voxel.getLongWithAdj(0, -1, 0, size));
            voxel.renderSpec[5] = !set.contains(voxel.getLongWithAdj(0, 1, 0, size));
            voxel.renderSpec[0] = !set.contains(voxel.getLongWithAdj(0, 0, 1, size));
            voxel.renderSpec[3] = !set.contains(voxel.getLongWithAdj(0, 0, -1, size));
            voxel.updateRenderCount();
        }
    }

    public static Voxel[] genVoxelArray(int count) {
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

    public MBO(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public MBO() {
        this.vector3D.x = 0.0f;
        this.vector3D.y = 0.0f;
        this.vector3D.z = 0.0f;
    }

    public Vector3D getVector3D() {
        return this.vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D.change(vector3D);
    }

    public float getX() {
        return vector3D.x;
    }

    public float getY() {
        return vector3D.y;
    }

    public float getZ() {
        return vector3D.z;
    }

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }
}
