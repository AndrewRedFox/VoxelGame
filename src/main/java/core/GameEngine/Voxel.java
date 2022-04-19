package core.GameEngine;

public class Voxel {
    private int x, y, z;
    public int renderCount;
    public boolean[] renderSpec;

    public Voxel(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
}
