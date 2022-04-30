package core.GameEngine.Character;

import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;

public class Character {

    private String name;
    private float health;
    private float damage;
    private float speed;
    private float jump;
    private Vector3D vector3D = new Vector3D();
    private RigidBody rigidBody = new RigidBody(5.0f, 2.0f, 0.0f, true);

    public Character() {
        name = "Nitler";
        health = 50.0f;
        damage = 10.0f;
        speed = 3.0f;
        jump = 2.0f;
    }

    public Character(String name, float health, float damage, float speed, float jump) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.jump = jump;
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

    public Vector3D getVector3D() {
        return vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
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
}
