package core.GameEngine.GameCore;

import core.GameEngine.GameCore.Collision.Collision;
import core.GameEngine.MBO;
import jglm.Vec;

public class Mass {

    //public float m; //значение массы
    public RigidBody rigidBody;
    public Vector3D pos = new Vector3D(); //положение в пространстве
    public Vector3D vel = new Vector3D(); //скорость
    public Vector3D force = new Vector3D(); //воздействующая сила
    public MBO object;
    public Collision collision;
    private Vector3D otherObjectPos;


    public Mass(float m, MBO object, MBOsObjects mbOsObjects) {
        //this.m = m;
        this.object = object;
        rigidBody = this.object.getRigidBody();
        //this.rigidBody.setMass(m);
        this.collision = new Collision(object, mbOsObjects);
        vel.changeY(rigidBody.getSpeed());
    }

    public Mass(MBO object, MBOsObjects mbOsObjects) {
        this.object = object;
        rigidBody = this.object.getRigidBody();
        this.collision = new Collision(object, mbOsObjects);
        vel.changeY(rigidBody.getSpeed());
    }


    public void rePosition() {
        this.pos.change(object.getVector3D());
    }

    public void applyForce(Vector3D force) { // Внешнюю силу прибавляем к «нашей»
        this.force.plusAndEqualsOperator(force);
    }

    public void init() { //обнуление силы
        force.x = 0;
        force.y = 0;
        force.z = 0;

    }

    public Vector3D getObj() {
        return pos;
    }

    public void impulsFunction(float dt) {

        rigidBody.setSpeed(Math.abs(0.4f * (2 * collision.getObjectCollision().getRigidBody().getMass() * collision.getObjectCollision().getRigidBody().getSpeed() + (rigidBody.getMass() - collision.getObjectCollision().getRigidBody().getMass()) * rigidBody.getSpeed()) / (rigidBody.getMass() + collision.getObjectCollision().getRigidBody().getMass())));//скорость обьекта 1 после столкновения
        collision.getObjectCollision().getRigidBody().setSpeed(Math.abs(0.4f * (2 * rigidBody.getMass() * rigidBody.getSpeed() + (collision.getObjectCollision().getRigidBody().getMass() - rigidBody.getMass()) * collision.getObjectCollision().getRigidBody().getSpeed()) / (rigidBody.getMass() + collision.getObjectCollision().getRigidBody().getMass())));
        Vector3D impuls = new Vector3D(0, rigidBody.getSpeed(), 0);
        Vector3D otherImpuls = new Vector3D(0,collision.getObjectCollision().getRigidBody().getSpeed(),0);
        if (vel.getY() < 0.0f) {
            vel.setY(vel.getY() * (-0.1f));
        }

        pos.plusAndEqualsOperator(impuls.multiplyOperator(dt));

        otherObjectPos = collision.getObjectCollision().getVector3D();
        otherObjectPos.plusAndEqualsOperator(otherImpuls.multiplyOperator(dt).multiplyOperator(-1.0f));
        collision.getObjectCollision().setVector3D(otherObjectPos);
    }

    public void simulate(float dt) { //dt это время
        rePosition();
        if (!collision.isCollision()) {
            vel.plusAndEqualsOperator((force.divideOperator(rigidBody.getMass())).multiplyOperator(dt)); //изменение скорости
            rigidBody.setSpeed(vel.getY());
            pos.plusAndEqualsOperator(vel.multiplyOperator(dt)); //изменение положения
            object.setVector3D(pos);
        } else {
            impulsFunction(dt);
        }
    }


}
