package core.GameEngine.GameCore;

import core.GameEngine.GameCore.Collision.Collision;
import core.GameEngine.MBO;
import jglm.Vec;

public class Mass {

    public float m; //значение массы
    public Vector3D pos = new Vector3D(); //положение в пространстве
    public Vector3D vel = new Vector3D(); //скорость
    public Vector3D force = new Vector3D(); //воздействующая сила
    public MBO object = new MBO();
    public Collision collision = new Collision(object);

    public Mass(float m, MBO object) {
        this.m = m;
        this.object = object;
    }

    public Mass() {
        m = 0;
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

    public void simulate(float dt) { //dt это время
        rePosition();
        if(!collision.isCollision()){
            vel.plusAndEqualsOperator((force.divideOperator(m)).multiplyOperator(dt)); //изменение скорости
            pos.plusAndEqualsOperator(vel.multiplyOperator(dt)); //изменение положения
            object.setVector3D(pos);
        }

    }


}
