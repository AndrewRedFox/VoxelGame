package core.GameEngine;

import core.GameEngine.GameCore.Vector3D;

public class MBO {
    Voxel[] voxels;
    private float x, y, z;
    private Vector3D vector3D = new Vector3D();//*
    private float angleX, angleY, angleZ;

    MBO(Voxel[] voxels, float x, float y, float z, float angleX, float angleY, float angleZ) {
        this.voxels = voxels;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
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
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
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
}
