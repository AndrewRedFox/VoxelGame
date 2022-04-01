package core.GameEngine.GameCore;

import java.lang.Math;

public class Vector3D {
    public float x;
    public float y;
    public float z;

    public Vector3D() {
        x = y = z = 0;
    }

    public Vector3D(float x, float y) {
        this.x = x;
        this.y = y;
        z = 0;
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public float dotProduct(Vector3D vec2) { /*скалярное произведение векторов*/
        return (x * vec2.x + y * vec2.y + z * vec2.z);
    }

    public Vector3D crossProduct(Vector3D vec2) {
        return new Vector3D();
    }

    public float lenght() {
        double d = Math.sqrt(x * x + y * y + z * z);
        return (float) d;
    }

    public void normalize() {
        float len = lenght();
        if (len != 0) {
            x /= len;
            y /= len;
            z /= len;
        }
    }

    public void change(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
    }

    public void change(Vector3D vec2) {
        this.x = vec2.x;
        this.y = vec2.y;
        this.z = vec2.z;
    }

    public void changeX(float x) {
        this.x = x;
    }

    public void changeY(float y) {
        this.y = y;
    }

    public void changeZ(float z) {
        this.z = z;
    }

    public Vector3D plusOperator(Vector3D vec2)// +
    {
        return new Vector3D(x + vec2.x, y + vec2.y, z + vec2.z);
    }

    public Vector3D minusOperator(Vector3D vec2) // -
    {
        return new Vector3D(x - vec2.x, y - vec2.y, z - vec2.z);
    }

    public Vector3D multiplyOperator(float num) // *
    {
        return new Vector3D(x * num, y * num, z * num);
    }

    public Vector3D divideOperator(float num) // /
    {
        if (num != 0) {
            return new Vector3D(x / num, y / num, z / num);
        } else return new Vector3D();
    }

    public Vector3D plusAndEqualsOperator(Vector3D vec2) // +=
    {
        x += vec2.x;
        y += vec2.y;
        z += vec2.z;
        return this;
    }

    public Vector3D minusAndEqualsOperator(Vector3D vec2) // -=
    {
        x -= vec2.x;
        y -= vec2.y;
        z -= vec2.z;
        return this;
    }

    public Vector3D multiplyAndEqualsOperator(float num) // *=
    {
        x *= num;
        y *= num;
        z *= num;
        return this;
    }

    public Vector3D divideAndEqualsOperator(float num) // /=
    {
        if (num != 0) {
            x /= num;
            y /= num;
            z /= num;
        }
        return this;
    }

    public boolean equalsOperator(Vector3D vec2) // ==
    {
        return (x == vec2.x && y == vec2.y && z == vec2.z);
    }

    public boolean notEqualsOperator(Vector3D vec2) // !=
    {
        return !(this == vec2);
    }

    public void printVect(Vector3D vec) {
        System.out.println(vec.x + " " + vec.y + " " + vec.z);
    }

}
