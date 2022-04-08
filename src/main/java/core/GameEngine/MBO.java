package core.GameEngine;

import core.GameEngine.GameCore.Vector3D;

public class MBO {
    public Voxel[] voxels;
    private Vector3D vector3D = new Vector3D();//*
    private float angleX, angleY, angleZ;
    public MBO(Voxel[] voxels, Vector3D vector3D, float angleX, float angleY, float angleZ){
        this.voxels = voxels;
        this.vector3D = vector3D;
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
}
