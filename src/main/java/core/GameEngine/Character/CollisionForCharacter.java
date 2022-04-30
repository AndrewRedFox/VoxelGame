package core.GameEngine.Character;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.MBO;

public class CollisionForCharacter {

    public Character character;
    public float voxelSize = GraphicsDisplay.voxelSize;
    public MBOsObjects mbOsObjects;
    //public MBO objectCollision;
    //private int objectCollisionTrigger;


    public CollisionForCharacter(Character character, MBOsObjects mbOsObjects) {
        this.character = character;
        this.mbOsObjects = mbOsObjects;
    }

    public void setObject(Character character) {
        this.character = character;
    }

    /*public MBO getObjectCollision(){
        return objectCollision;
    }*/

    public boolean isCollision() {
        boolean flag = false;
        for (int i = 0; i < 1; i++) {
            double o1x = character.getX() + 1 * voxelSize;
            double o1y = character.getY() + 2 * voxelSize;
            double o1z = character.getZ() + 1 * voxelSize;
            for (int j = 0; j < mbOsObjects.size(); j++) {
                    for (int k = 0; k < mbOsObjects.getByIndex(j).voxels.length; k++) {
                        double o2x = mbOsObjects.getByIndex(j).getX() + mbOsObjects.getByIndex(j).voxels[k].getX() * voxelSize;
                        double o2y = mbOsObjects.getByIndex(j).getY() + mbOsObjects.getByIndex(j).voxels[k].getY() * voxelSize;
                        double o2z = mbOsObjects.getByIndex(j).getZ() + mbOsObjects.getByIndex(j).voxels[k].getZ() * voxelSize;
                        //System.out.println(o2x + " " + o2y + " " + o2z);
                        if (o1x > o2x - voxelSize && o1x < o2x + voxelSize && o1y > o2y - voxelSize && o1y < o2y + voxelSize && o1z > o2z - voxelSize && o1z < o2z + voxelSize) {
                            flag = true;
                            //objectCollision = mbOsObjects.getByIndex(j);
                        }
                    }
            }
            return flag;
        }
        return false;
    }
}
