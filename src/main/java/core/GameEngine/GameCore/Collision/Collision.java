package core.GameEngine.GameCore.Collision;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.GameEngine.PhysicsCore;
import core.GameEngine.Voxel;
import core.Launcher;

public class Collision {
    public MBO object;
    public float voxelSize = GraphicsDisplay.voxelSize;
    public MBO[] mas = MBOsObjects.getMBOs();


    public Collision(MBO object) {
        this.object = object;
    }

    public void setObject(MBO object) {
        this.object = object;
    }

    public boolean isCollision() {
        boolean flag = false;
        for (int i = 0; i < object.voxels.length; i++) {
            double o1x = object.getX() + object.voxels[i].getX() * voxelSize;
            double o1y = object.getY() + object.voxels[i].getY() * voxelSize;
            double o1z = object.getZ() + object.voxels[i].getZ() * voxelSize;

            for (int j = 0; j < mas.length; j++) {
                if(mas[j]!=object) {
                    flag = false;
                    for (int k = 0; k < mas[j].voxels.length; k++) {
                        double o2x = mas[j].getX() + mas[j].voxels[k].getX() * voxelSize;
                        double o2y = mas[j].getY() + mas[j].voxels[k].getY() * voxelSize;
                        double o2z = mas[j].getZ() + mas[j].voxels[k].getZ() * voxelSize;
                        //System.out.println(o2x + " " + o2y + " " + o2z);
                        if (o1x > o2x - voxelSize && o1x < o2x + voxelSize && o1y > o2y - voxelSize && o1y < o2y + voxelSize && o1z > o2z - voxelSize && o1z < o2z + voxelSize) {
                            flag = true;
                        }
                    }
                }
            }
            return flag;
        }
        return false;
    }

}
