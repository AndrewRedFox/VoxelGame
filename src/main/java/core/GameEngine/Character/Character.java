package core.GameEngine.Character;

import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;
import jglm.Vec;

public class Character {

    private String name;
    private float health;
    private float damage;
    private float speed;
    private float jump;
    private Vector3D vectorPos = new Vector3D();
    private RigidBody rigidBody = new RigidBody(5.0f, 2.0f, 0.0f, true);

    public Character() {
        name = "Nitler";
        health = 50.0f;
        damage = 10.0f;
        speed = 0.1f;
        jump = 2.0f;
        vectorPos = new Vector3D(0, 0, 0);
    }

    public Character(Vector3D vectorPos, String name, float health, float damage, float speed, float jump) {
        this.vectorPos = vectorPos;
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.jump = jump;
    }

    public void adjustPos(float dx, float dy, float dz) {
        vectorPos.x += dx;
        vectorPos.y += dy;
        vectorPos.z += dz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getJump() {
        return jump;
    }

    public void setJump(float jump) {
        this.jump = jump;
    }

    public Vector3D getVectorPos() {
        return vectorPos;
    }

    public void setVectorPos(Vector3D vectorPos) {
        this.vectorPos.change(vectorPos);
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    public float getX() {
        return vectorPos.x;
    }

    public float getY() {
        return vectorPos.y;
    }

    public float getZ() {
        return vectorPos.z;
    }
}
