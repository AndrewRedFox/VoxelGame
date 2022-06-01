package core.GameEngine;

public class Voxel {
    private int x, y, z;
    private float angelX, AngelY,AngelZ;
    public int renderCount;
    public int[] textureIds;
    public boolean[] renderSpec;

    public Voxel(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        textureIds = new int[]{0,0,0,0,0,0};
        renderSpec = new boolean[]{true, true, true, true, true, true};
    }

    public long getLongWithAdj(long dx, long dy, long dz, long size) {
        return (x + dx) * size * size + (y + dy) * size + (z + dz);
    }

    public void updateRenderCount() {
        renderCount = 0;
        for (int i = 0; i < 6; i++) {
            if (renderSpec[i]) renderCount++;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public float getAngelX() {
        return angelX;
    }

    public void setAngelX(float angelX) {
        this.angelX = angelX;
    }

    public float getAngelY() {
        return AngelY;
    }

    public void setAngelY(float angelY) {
        AngelY = angelY;
    }

    public float getAngelZ() {
        return AngelZ;
    }

    public void setAngelZ(float angelZ) {
        AngelZ = angelZ;
    }
}
