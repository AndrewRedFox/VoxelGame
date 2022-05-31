package core.GameEngine;

import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;
//import jdk.incubator.vector.VectorOperators;

import java.util.HashSet;
import java.util.Random;

public class MBO {
    public Voxel[] voxels;
    private Vector3D vector3D = new Vector3D();//position
    private float angleX, angleY, angleZ;
    private RigidBody rigidBody = new RigidBody(5.0f, 2.0f, 0.0f, true);

    public MBO(Voxel[] voxels, Vector3D vector3D, RigidBody rigidBody, float angleX, float angleY, float angleZ) {
        this.voxels = voxels;
        this.vector3D = vector3D;
        this.rigidBody = rigidBody;
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
            while (set.contains(cord[0] * 100000000 + cord[1] * 10000 + cord[2])) {
                cord[new Random().nextInt(3)] += (new Random().nextBoolean() ? 1 : -1);
            }
        }
        return mas;
    }

    public static Voxel[] genShip(){
        Voxel[] mas = new Voxel[27];
        mas[0] = new Voxel(0, 0, 0);
        mas[1] = new Voxel(1, 0, 0);
        mas[2] = new Voxel(2, 0, 0);
        mas[3] = new Voxel(3, 0, 0);
        mas[4] = new Voxel(4, 0, 0);
        mas[5] = new Voxel(-1, 0, 0);
        mas[6] = new Voxel(-2, 0, 0);
        mas[7] = new Voxel(-3, 0, 0);
        mas[8] = new Voxel(-4, 0, 0);
        mas[9] = new Voxel(0, 0, 1);
        mas[10] = new Voxel(1, 0, 1);
        mas[11] = new Voxel(2, 0, 1);
        mas[12] = new Voxel(3, 0, 1);
        mas[13] = new Voxel(4, 0, 1);
        mas[14] = new Voxel(-1, 0, 1);
        mas[15] = new Voxel(-2, 0, 1);
        mas[16] = new Voxel(-3, 0, 1);
        mas[17] = new Voxel(-4, 0,1);
        mas[18] = new Voxel(0, 0, 2);
        mas[19] = new Voxel(1, 0, 2);
        mas[20] = new Voxel(2, 0, 2);
        mas[21] = new Voxel(3, 0, 2);
        mas[22] = new Voxel(4, 0, 2);
        mas[23] = new Voxel(-1, 0, 2);
        mas[24] = new Voxel(-2, 0, 2);
        mas[25] = new Voxel(-3, 0, 2);
        mas[26] = new Voxel(-4, 0,2);
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

    public RigidBody getRigidBody(){
        return rigidBody;
    }
}
