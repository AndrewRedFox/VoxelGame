package Renderer;

import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;
import core.GameEngine.Voxel;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class ArrayContainer {
    float[] vertices;
    int[] indices;
    private int mboSLength;
    private final PhysicsCore physicsCore;
    ArrayContainer(PhysicsCore physicsCore){
        this.physicsCore = physicsCore;
    }

    @Deprecated
    private int countVoxels(MBO[] mboS) {
        int sum = 0;
        for (MBO mbo : mboS) {
            for (Voxel voxel : mbo.voxels) {
                sum += voxel.renderCount;
            }
        }
        return sum;
    }

    private int countVoxels(MBOsObjects mbOsObjects) {
        int sum = 0;
        for(int i = 0; i < mbOsObjects.size(); i++){
            for (Voxel voxel : mbOsObjects.getByIndex(i).voxels) {
                sum += voxel.renderCount;
            }
        }
        return sum;
    }

    private static final int[] indicesTemp = {

            0, 2, 1,
            0, 3, 2

    };

    void refreshMBOS(){
        //synchronized (physicsCore) {
            mboSLength = countVoxels(physicsCore.mbOsObjects);
            vertices = new float[mboSLength * 20];
            indices = new int[mboSLength * 6];

            int idVoxelPart = 0;
            for (int i = 0; i < physicsCore.mbOsObjects.size(); i++) {
                MBO mbo = physicsCore.mbOsObjects.getByIndex(i);
                final float mboX = mbo.getX(), mboY = mbo.getY(), mboZ = mbo.getZ();
                mbo.setAngleX(mbo.getAngleX() + 0.01f);
                mbo.setAngleY(mbo.getAngleY() + 0.01f);
                mbo.setAngleZ(mbo.getAngleZ() + 0.01f);
                final float xRot = mbo.getAngleX(), yRot = mbo.getAngleY(), zRot = mbo.getAngleZ();
                for (Voxel voxel : mbo.voxels) {
                    setMboVertices(voxel, mboX, mboY, mboZ, idVoxelPart, xRot, yRot, zRot);
                    idVoxelPart+= voxel.renderCount;
                    //System.out.println(mboSLength + "\t" + idVoxelPart);
                }
            }
        //}
    }

    private void setMboVertices(Voxel voxel, float x, float y, float z, int startIndex, float xRot, float yRot, float zRot) {
        final float voxelSize = GraphicsDisplay.voxelSize;
        final Matrix3f matrix3f = new Matrix3f().rotationXYZ(xRot, yRot, zRot);
        final Vector3f lineX = new Vector3f(voxelSize, 0.0f, 0.0f).mul(matrix3f);//.normalize(voxelSize);
        final Vector3f lineY = new Vector3f(0.0f, voxelSize, 0.0f).mul(matrix3f);//.normalize(voxelSize);
        final Vector3f lineZ = new Vector3f(0.0f, 0.0f, -voxelSize).mul(matrix3f);//.normalize(voxelSize);
        final float voxelX = x + lineX.x * voxel.getX() + lineY.x * voxel.getY() + lineZ.x * voxel.getZ();
        final float voxelY = y + lineX.y * voxel.getX() + lineY.y * voxel.getY() + lineZ.y * voxel.getZ();
        final float voxelZ = z + lineX.z * voxel.getX() + lineY.z * voxel.getY() + lineZ.z * voxel.getZ();

        float[] verticesTemp = {

                voxelX                    , voxelY                   , voxelZ                     , 0.0f, 0.5f,//South
                voxelX + lineY.x          , voxelY + lineY.y         , voxelZ + lineY.z           , 0.0f, 1.0f,
                voxelX + lineX.x + lineY.x, voxelY+ lineX.y + lineY.y, voxelZ + lineX.z  + lineY.z, 0.25f, 1.0f,
                voxelX + lineX.x          , voxelY + lineX.y         , voxelZ + lineX.z           , 0.25f, 0.5f
                ,


                voxelX + lineX.x                    , voxelY + lineX.y                     , voxelZ + lineX.z                    , 0.5f, 0.5f,//East
                voxelX + lineX.x + lineY.x          , voxelY + lineX.y + lineY.y           , voxelZ + lineX.z + lineY.z          , 0.5f, 1.0f,
                voxelX + lineX.x + lineY.x + lineZ.x, voxelY + lineX.y + lineY.y + lineZ.y , voxelZ + lineX.z + lineY.z + lineZ.z, 0.75f, 1.0f,
                voxelX + lineX.x + lineZ.x          , voxelY + lineX.y + lineZ.y           , voxelZ + lineX.z + lineZ.z          , 0.75f, 0.5f
                ,


                voxelX + lineY.x + lineZ.x, voxelY + lineY.y + lineZ.y, voxelZ + lineY.z + lineZ.z, 0.75f, 1.0f,//West
                voxelX + lineY.x          , voxelY + lineY.y          , voxelZ + lineY.z          , 1.0f, 1.0f,
                voxelX                    , voxelY                    , voxelZ                    , 1.0f, 0.5f,
                voxelX + lineZ.x          , voxelY + lineZ.y          , voxelZ + lineZ.z          , 0.75f, 0.5f
                ,


                voxelX + lineX.x + lineZ.x           , voxelY + lineX.y + lineZ.y           , voxelZ + lineX.z + lineZ.z           , 0.25f, 0.5f,//North
                voxelX + lineX.x + lineY.x  + lineZ.x, voxelY + lineX.y + lineY.y  + lineZ.y, voxelZ + lineX.z + lineY.z  + lineZ.z, 0.25f, 1.0f,
                voxelX + lineY.x + lineZ.x           , voxelY + lineY.y + lineZ.y           , voxelZ + lineY.z+ lineZ.z            , 0.5f, 1.0f,
                voxelX + lineZ.x                     , voxelY + lineZ.y                     , voxelZ + lineZ.z                     , 0.5f, 0.5f
                ,


                voxelX + lineX.x + lineZ.x, voxelY + lineX.y + lineZ.y, voxelZ + lineX.z + lineZ.z, 0.5f, 0.5f,//Down
                voxelX + lineZ.x          , voxelY + lineZ.y          , voxelZ + lineZ.z          , 0.25f, 0.5f,
                voxelX                    , voxelY                    , voxelZ                    , 0.25f, 0.0f,
                voxelX + lineX.x          , voxelY + lineX.y          , voxelZ + lineX.z          , 0.5f, 0.0f
                ,


                voxelX + lineY.x                    , voxelY + lineY.y                    , voxelZ + lineY.z                    , 0.25f, 0.0f,//Up
                voxelX + lineY.x + lineZ.x          , voxelY + lineY.y + lineZ.y          , voxelZ + lineY.z + lineZ.z          , 0.25f, 0.5f,
                voxelX + lineX.x + lineY.x + lineZ.x, voxelY + lineX.y + lineY.y + lineZ.y, voxelZ + lineX.z + lineY.z + lineZ.z, 0.0f, 0.5f,
                voxelX + lineX.x + lineY.x          , voxelY + lineX.y + lineY.y          , voxelZ + lineX.z + lineY.z          , 0.0f, 0.0f

        };

        int promIndex = 0;
        for(int i = 0; i < 6; i++) {
            if (voxel.renderSpec[i]) {
                for (int j = 0; j < 20; j++) {
                    vertices[startIndex * 20 + j +  promIndex*20] = verticesTemp[j +  i*20];
                }
                for (int j = 0; j < 6; j++) {
                    indices[startIndex * 6 + j +  promIndex*6] = indicesTemp[j] + promIndex * 4 + startIndex * 4;
                    //System.out.println(startIndex * 6 + j +  promIndex*6 + "\tof " + (mboSLength * 6 - 1));
                }
                promIndex++;
            }
        }
        //System.out.println(1);
    }

    private void setSquareVertices(Vector3D a, Vector3D b, Vector3D c, Vector3D d){
    }
}
