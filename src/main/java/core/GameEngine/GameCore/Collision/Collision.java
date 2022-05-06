package core.GameEngine.GameCore.Collision;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.MBO;

public class Collision {
    public MBO object;
    public float voxelSize = GraphicsDisplay.voxelSize;
    public MBOsObjects mbOsObjects;
    public MBO objectCollision;

    public Collision(MBO object, MBOsObjects mbOsObjects) {
        this.object = object;
        this.mbOsObjects = mbOsObjects;
    }

    public void setObject(MBO object) {
        this.object = object;
    }

    public MBO getObjectCollision(){
        return objectCollision;
    }

    private final int threadCount = 10;

    private boolean collisionResult = false;

    public boolean isCollision() {
        collisionResult = false;

        int auxSize = object.voxels.length/threadCount;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = runAuxThread( i*auxSize, (i+1)*auxSize);
        }
        for (Thread threadA: threads) {
            try {
                threadA.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return collisionResult;
    }

    private Thread runAuxThread(int start, int end){
        Thread auxThread = new Thread(() -> {
            st:
            for (int i = start; i < Math.min(end, object.voxels.length); i++) {
                final double o1x = object.getX() + object.voxels[i].getX()*voxelSize;
                final double o1y = object.getY() + object.voxels[i].getY()*voxelSize;
                final double o1z = object.getZ() + object.voxels[i].getZ()*voxelSize;
                for (int j = 0; j < mbOsObjects.size(); j++) {
                    if (mbOsObjects.getByIndex(j) != object) {
                        for (int k = 0; k < mbOsObjects.getByIndex(j).voxels.length; k++) {
                            double o2x = mbOsObjects.getByIndex(j).getX() + mbOsObjects.getByIndex(j).voxels[k].getX()*voxelSize;
                            double o2y = mbOsObjects.getByIndex(j).getY() + mbOsObjects.getByIndex(j).voxels[k].getY()*voxelSize;
                            double o2z = mbOsObjects.getByIndex(j).getZ() + mbOsObjects.getByIndex(j).voxels[k].getZ()*voxelSize;
                            if (o1x > o2x - voxelSize && o1x < o2x + voxelSize && o1y > o2y - voxelSize && o1y < o2y + voxelSize && o1z > o2z - voxelSize && o1z < o2z + voxelSize) {
                                objectCollision = mbOsObjects.getByIndex(j);
                                collisionResult = true;
                                break st;
                            }
                        }
                    }
                }
            }
        });
        auxThread.start();
        return auxThread;
    }

}
