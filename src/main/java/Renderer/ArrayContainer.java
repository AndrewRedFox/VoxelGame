package Renderer;

import core.GameEngine.MBO;
import core.GameEngine.Voxel;
import core.Launcher;

public class ArrayContainer {
    float[] vertices;
    int[] indices;
    private int mboSLength;
    private Launcher launcher;
    ArrayContainer(Launcher launcher){
        this.launcher = launcher;
    }

    private int countVoxels(MBO[] mboS) {
        int sum = 0;
        for (MBO mbo : mboS) {
            for (Voxel voxel : mbo.voxels) {
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
        synchronized (launcher) {
            mboSLength = countVoxels(launcher.mbos);
            vertices = new float[mboSLength * 20];
            indices = new int[mboSLength * 6];

            int idVoxelPart = 0;
            for (MBO mbo : launcher.mbos) {
                float mboX = mbo.getX(), mboY = mbo.getY(), mboZ = mbo.getZ();
                for (Voxel voxel : mbo.voxels) {
                    setMboVertices(voxel, mboX, mboY, mboZ, idVoxelPart);
                    idVoxelPart+= voxel.renderCount;
                    //System.out.println(mboSLength + "\t" + idVoxelPart);
                }
            }
        }
    }

    private void setMboVertices(Voxel voxel, float x, float y, float z, int startIndex) {
        final float voxelSize = GraphicsDisplay.voxelSize;
        float voxelX = x + voxel.getX() * voxelSize, voxelY = y + voxel.getY() * voxelSize, voxelZ = z + voxel.getZ() * voxelSize;

        float[] verticesTemp = {

                voxelX,             voxelY,             voxelZ,             0.0f, 0.5f,
                voxelX,             voxelY + voxelSize, voxelZ,             0.0f, 1.0f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ,             0.25f, 1.0f,
                voxelX + voxelSize, voxelY,             voxelZ,             0.25f, 0.5f
                ,


                voxelX + voxelSize, voxelY,             voxelZ,             0.5f, 0.5f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ,             0.5f, 1.0f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ - voxelSize, 0.75f, 1.0f,
                voxelX + voxelSize, voxelY,             voxelZ - voxelSize, 0.75f, 0.5f
                ,


                voxelX,             voxelY,             voxelZ,             1.0f, 0.5f,
                voxelX,             voxelY + voxelSize, voxelZ,             1.0f, 1.0f,
                voxelX,             voxelY + voxelSize, voxelZ - voxelSize, 0.75f, 1.0f,
                voxelX,             voxelY,             voxelZ - voxelSize, 0.75f, 0.5f
                ,


                voxelX,             voxelY,             voxelZ - voxelSize, 0.5f, 0.5f,
                voxelX,             voxelY + voxelSize, voxelZ - voxelSize, 0.5f, 1.0f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ - voxelSize, 0.25f, 1.0f,
                voxelX + voxelSize, voxelY,             voxelZ - voxelSize, 0.25f, 0.5f
                ,


                voxelX,             voxelY,             voxelZ,             0.5f, 0.0f,
                voxelX,             voxelY,             voxelZ - voxelSize, 0.5f, 0.5f,
                voxelX + voxelSize, voxelY,             voxelZ - voxelSize, 0.25f, 0.5f,
                voxelX + voxelSize, voxelY,             voxelZ,             0.25f, 0.0f
                ,


                voxelX,             voxelY + voxelSize, voxelZ,             0.25f, 0.0f,
                voxelX,             voxelY + voxelSize, voxelZ - voxelSize, 0.25f, 0.5f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ - voxelSize, 0.0f, 0.5f,
                voxelX + voxelSize, voxelY + voxelSize, voxelZ,             0.0f, 0.0f

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
}
