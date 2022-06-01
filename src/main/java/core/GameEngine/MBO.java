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
            voxel.renderSpec[3] = !set.contains(voxel.getLongWithAdj(0, 0, 1, size));
            voxel.renderSpec[0] = !set.contains(voxel.getLongWithAdj(0, 0, -1, size));
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

    public static Voxel[] genShip(){
        Voxel[] mas = new Voxel[137];
        mas[0] = new Voxel(0, 0, 0);
        mas[1] = new Voxel(0, 0, 1);
        mas[2] = new Voxel(0, 0, 2);
        mas[3] = new Voxel(0, 0, 3);
        mas[4] = new Voxel(0, 0, 4);
        mas[5] = new Voxel(0, 0, -1);
        mas[6] = new Voxel(0, 0, -2);
        mas[7] = new Voxel(0, 0, -3);
        mas[8] = new Voxel(0, 0, -4);
        mas[9] = new Voxel(1, 0, 0);
        mas[10] = new Voxel(1, 0, 1);
        mas[11] = new Voxel(1, 0, 2);
        mas[12] = new Voxel(1, 0, 3);
        mas[13] = new Voxel(1, 0, 4);
        mas[14] = new Voxel(1, 0, -1);
        mas[15] = new Voxel(1, 0, -2);
        mas[16] = new Voxel(1, 0, -3);
        mas[17] = new Voxel(1, 0,-4);
        mas[18] = new Voxel(2, 0, 0);
        mas[19] = new Voxel(2, 0, 1);
        mas[20] = new Voxel(2, 0, 2);
        mas[21] = new Voxel(2, 0, 3);
        mas[22] = new Voxel(2, 0, 4);
        mas[23] = new Voxel(2, 0, -1);
        mas[24] = new Voxel(2, 0, -2);
        mas[25] = new Voxel(2, 0, -3);
        mas[26] = new Voxel(2, 0,-4);
        mas[27] = new Voxel(3, 0, 0);
        mas[28] = new Voxel(3, 0, 1);
        mas[29] = new Voxel(3, 0, 2);
        mas[30] = new Voxel(3, 0, 3);
        mas[31] = new Voxel(3, 0, 4);
        mas[32] = new Voxel(3, 0, -1);
        mas[33] = new Voxel(3, 0, -2);
        mas[34] = new Voxel(3, 0, -3);
        mas[35] = new Voxel(3, 0,-4);
        mas[36] = new Voxel(4, 0, 0);
        mas[37] = new Voxel(4, 0, 1);
        mas[38] = new Voxel(4, 0, 2);
        mas[39] = new Voxel(4, 0, 3);
        mas[40] = new Voxel(4, 0, 4);
        mas[41] = new Voxel(4, 0, -1);
        mas[42] = new Voxel(4, 0, -2);
        mas[43] = new Voxel(4, 0, -3);
        mas[44] = new Voxel(4, 0,-4);
        mas[45] = new Voxel(0, 0, -5);
        mas[46] = new Voxel(0, 0, -6);
        mas[47] = new Voxel(1, 0, -5);
        mas[48] = new Voxel(1, 0, -6);
        mas[49] = new Voxel(2, 0, -5);
        mas[50] = new Voxel(2, 0, -6);
        mas[51] = new Voxel(3, 0, -5);
        mas[52] = new Voxel(3, 0, -6);
        mas[53] = new Voxel(4, 0, -5);
        mas[54] = new Voxel(4, 0, -6);
        mas[55] = new Voxel(3, 0, 5);
        mas[56] = new Voxel(1, 0, 5);
        mas[57] = new Voxel(2, 0, 5);
        mas[58] = new Voxel(3, 0, 6);
        mas[59] = new Voxel(1, 0, 6);
        mas[60] = new Voxel(2, 0, 6);
        mas[61] = new Voxel(2, 0, 7);
        mas[62] = new Voxel(2, 0, 8);
        mas[63] = new Voxel(2, 1, 9);
        mas[64] = new Voxel(1, 1, 8);
        mas[65] = new Voxel(1, 1, 7);
        mas[66] = new Voxel(3, 1, 8);
        mas[67] = new Voxel(3, 1, 7);
        mas[68] = new Voxel(0, 1, 6);
        mas[69] = new Voxel(0, 1, 5);
        mas[70] = new Voxel(4, 1, 6);
        mas[71] = new Voxel(4, 1, 5);
        mas[72] = new Voxel(-1, 1, 4);
        mas[73] = new Voxel(-1, 1, 3);
        mas[74] = new Voxel(-1, 1, 2);
        mas[75] = new Voxel(-1, 1, 1);
        mas[76] = new Voxel(-1, 1, 0);
        mas[77] = new Voxel(-1, 1, -1);
        mas[78] = new Voxel(-1, 1, -2);
        mas[79] = new Voxel(-1, 1, -3);
        mas[80] = new Voxel(-1, 1, -4);
        mas[81] = new Voxel(-1, 1, -5);
        mas[82] = new Voxel(-1, 1, -6);
        mas[83] = new Voxel(5, 1, 4);
        mas[84] = new Voxel(5, 1, 3);
        mas[85] = new Voxel(5, 1, 2);
        mas[86] = new Voxel(5, 1, 1);
        mas[87] = new Voxel(5, 1, 0);
        mas[88] = new Voxel(5, 1, -1);
        mas[89] = new Voxel(5, 1, -2);
        mas[90] = new Voxel(5, 1, -3);
        mas[91] = new Voxel(5, 1, -4);
        mas[92] = new Voxel(5, 1, -5);
        mas[93] = new Voxel(5, 1, -6);
        mas[94] = new Voxel(5, 1, -7);
        mas[95] = new Voxel(4, 1, -7);
        mas[96] = new Voxel(3, 1, -7);
        mas[97] = new Voxel(2, 1, -7);
        mas[98] = new Voxel(1, 1, -7);
        mas[99] = new Voxel(0, 1, -7);
        mas[100] = new Voxel(-1, 1, -7);
        mas[101] = new Voxel(2, 1, 2);
        mas[102] = new Voxel(2, 2, 2);
        mas[103] = new Voxel(2, 3, 2);
        mas[104] = new Voxel(2, 4, 2);
        mas[105] = new Voxel(2, 5, 2);
        mas[106] = new Voxel(2, 6, 2);
        mas[107] = new Voxel(2, 7, 2);
        mas[108] = new Voxel(2, 8, 2);
        mas[109] = new Voxel(2, 9, 2);

        mas[110] = new Voxel(2, 1, -2);
        mas[111] = new Voxel(2, 2, -2);
        mas[112] = new Voxel(2, 3, -2);

        mas[113] = new Voxel(3, 2, -2);

        mas[114] = new Voxel(1, 3, -2);

        mas[115] = new Voxel(3, 2, -3);

        mas[116] = new Voxel(1, 3, -3);

        mas[117] = new Voxel(3, 2, -4);

        mas[118] = new Voxel(1, 3, -4);

        mas[119] = new Voxel(2, 3, -4);

        mas[120] = new Voxel(3, 1, -2);
        mas[121] = new Voxel(3, 3, -2);
        mas[122] = new Voxel(1, 1, -2);
        mas[123] = new Voxel(1, 2, -2);
        mas[124] = new Voxel(3, 1, -3);
        mas[125] = new Voxel(3, 3, -3);
        mas[126] = new Voxel(1, 1, -3);
        mas[127] = new Voxel(1, 2, -3);
        mas[128] = new Voxel(3, 1, -4);
        mas[129] = new Voxel(3, 3, -4);
        mas[130] = new Voxel(1, 2, -4);
        mas[131] = new Voxel(1, 1, -4);
        mas[132] = new Voxel(2, 1, -4);
        mas[133] = new Voxel(2, 2, -4);
        mas[134] = new Voxel(2, 3, -3);
        mas[135] = new Voxel(1, 7, 2);
        mas[136] = new Voxel(3, 7, 2);

        return mas;
    }
}
