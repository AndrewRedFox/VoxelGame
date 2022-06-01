package Renderer;

import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;
import core.GameEngine.Voxel;
import org.joml.Matrix3f;
import org.joml.Vector3f;

//Преобразователь и хранилище для информации о вершинах
public class ArrayContainer {
    float[] vertices; //информация о вершинах
    int[] indices; //индексы
    private final PhysicsCore physicsCore;//ссылка на физический движок
    private final float texturePrecision = 8.0f;

    ArrayContainer(PhysicsCore physicsCore) {
        this.physicsCore = physicsCore;
    }

    private int countVoxels(MBOsObjects mbOsObjects) {
        int sum = 0;
        for (int i = 0; i < mbOsObjects.size(); i++) {
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

    void refreshMBOS() {
        final int mboSLength = countVoxels(physicsCore.mbOsObjects);//подсчёт количества вершин
        vertices = new float[mboSLength * 20];
        indices = new int[mboSLength * 6];
        int idVoxelPart = 0;
        for (int i = 0; i < physicsCore.mbOsObjects.size(); i++) {
            MBO mbo = physicsCore.mbOsObjects.getByIndex(i);
            final float mboX = mbo.getX(), mboY = mbo.getY(), mboZ = mbo.getZ();
            final float xRot = mbo.getAngleX(), yRot = mbo.getAngleY(), zRot = mbo.getAngleZ();
            for (Voxel voxel : mbo.voxels) {
                setMboVertices(voxel, mboX, mboY, mboZ, idVoxelPart, xRot, yRot, zRot);
                idVoxelPart += voxel.renderCount;
            }
        }
    }

    //трансляция информации о Voxel в массивы информации вершин и индексов
    private void setMboVertices(Voxel voxel, float x, float y, float z, int startIndex, float xRot, float yRot, float zRot) {
        final float voxelSize = GraphicsDisplay.voxelSize;
        final Matrix3f matrix3f = new Matrix3f().rotationXYZ(xRot, yRot, zRot); //Матрица поворота
        final Vector3f lineX = new Vector3f(voxelSize, 0.0f, 0.0f).mul(matrix3f); //базис OZ
        final Vector3f lineY = new Vector3f(0.0f, voxelSize, 0.0f).mul(matrix3f); //базис OY
        final Vector3f lineZ = new Vector3f(0.0f, 0.0f, -voxelSize).mul(matrix3f);//базис OX
        final float voxelX = x + lineX.x * voxel.getX() + lineY.x * voxel.getY() + lineZ.x * voxel.getZ();
        final float voxelY = y + lineX.y * voxel.getX() + lineY.y * voxel.getY() + lineZ.y * voxel.getZ();
        final float voxelZ = z + lineX.z * voxel.getX() + lineY.z * voxel.getY() + lineZ.z * voxel.getZ();

        final float delta = 1.0f / texturePrecision;
        final float[] xId = new float[6];
        final float[] yId = new float[6];

        for (int i = 0; i < 6; i++) {
            xId[i] = (float) (voxel.textureIds[0] / 16L) / texturePrecision;
            yId[i] = (float) (voxel.textureIds[0] % 16L) / texturePrecision;
        }

        //Тип информации идёт в разнобой (координаты + параметры в одном месте),
        //но в любом случае в шейдере всё читается 'построчно'(через layouts)
        float[] verticesTemp = {
                //X, Y, Z, dX, dY (dX, dY) - координаты в textureMap
                voxelX, voxelY, voxelZ, xId[0], yId[0],                                                              //South
                voxelX + lineY.x, voxelY + lineY.y, voxelZ + lineY.z, xId[0], yId[0] + delta,
                voxelX + lineX.x + lineY.x, voxelY + lineX.y + lineY.y, voxelZ + lineX.z + lineY.z, xId[0] + delta, yId[0] + delta,
                voxelX + lineX.x, voxelY + lineX.y, voxelZ + lineX.z, xId[0] + delta, yId[0]
                ,
                voxelX + lineX.x, voxelY + lineX.y, voxelZ + lineX.z, xId[1], yId[1],                            //East
                voxelX + lineX.x + lineY.x, voxelY + lineX.y + lineY.y, voxelZ + lineX.z + lineY.z, xId[1], yId[1] + delta,
                voxelX + lineX.x + lineY.x + lineZ.x, voxelY + lineX.y + lineY.y + lineZ.y, voxelZ + lineX.z + lineY.z + lineZ.z, xId[1] + delta, yId[1] + delta,
                voxelX + lineX.x + lineZ.x, voxelY + lineX.y + lineZ.y, voxelZ + lineX.z + lineZ.z, xId[1] + delta, yId[1]
                ,
                voxelX + lineY.x + lineZ.x, voxelY + lineY.y + lineZ.y, voxelZ + lineY.z + lineZ.z, xId[2] + delta, yId[2],  //West
                voxelX + lineY.x, voxelY + lineY.y, voxelZ + lineY.z, xId[2] + delta, yId[2] + delta,
                voxelX, voxelY, voxelZ, xId[2], yId[2] + delta,
                voxelX + lineZ.x, voxelY + lineZ.y, voxelZ + lineZ.z, xId[2], yId[2]
                ,
                voxelX + lineX.x + lineZ.x, voxelY + lineX.y + lineZ.y, voxelZ + lineX.z + lineZ.z, xId[3], yId[3],  //North
                voxelX + lineX.x + lineY.x + lineZ.x, voxelY + lineX.y + lineY.y + lineZ.y, voxelZ + lineX.z + lineY.z + lineZ.z, xId[3], yId[3] + delta,
                voxelX + lineY.x + lineZ.x, voxelY + lineY.y + lineZ.y, voxelZ + lineY.z + lineZ.z, xId[3] + delta, yId[3] + delta,
                voxelX + lineZ.x, voxelY + lineZ.y, voxelZ + lineZ.z, xId[3] + delta, yId[3]
                ,
                voxelX + lineX.x + lineZ.x, voxelY + lineX.y + lineZ.y, voxelZ + lineX.z + lineZ.z, xId[4], yId[4] + delta,   //Down
                voxelX + lineZ.x, voxelY + lineZ.y, voxelZ + lineZ.z, xId[4], yId[4],
                voxelX, voxelY, voxelZ, xId[4] + delta, yId[4],
                voxelX + lineX.x, voxelY + lineX.y, voxelZ + lineX.z, xId[4] + delta, yId[4] + delta
                ,
                voxelX + lineY.x, voxelY + lineY.y, voxelZ + lineY.z, xId[5], yId[5],                            //Up
                voxelX + lineY.x + lineZ.x, voxelY + lineY.y + lineZ.y, voxelZ + lineY.z + lineZ.z, xId[5], yId[5] + delta,
                voxelX + lineX.x + lineY.x + lineZ.x, voxelY + lineX.y + lineY.y + lineZ.y, voxelZ + lineX.z + lineY.z + lineZ.z, xId[5] + delta, yId[5] + delta,
                voxelX + lineX.x + lineY.x, voxelY + lineX.y + lineY.y, voxelZ + lineX.z + lineY.z, xId[5] + delta, yId[5]
        };

        int promIndex = 0;
        for (int i = 0; i < 6; i++) {
            if (voxel.renderSpec[i]) {
                for (int j = 0; j < 20; j++) {
                    vertices[startIndex * 20 + j + promIndex * 20] = verticesTemp[j + i * 20];
                }
                for (int j = 0; j < 6; j++) {
                    indices[startIndex * 6 + j + promIndex * 6] = indicesTemp[j] + promIndex * 4 + startIndex * 4;
                }
                promIndex++;
            }
        }
    }

    private void setSquareVertices(Vector3D a, Vector3D b, Vector3D c, Vector3D d) {
    }
}
