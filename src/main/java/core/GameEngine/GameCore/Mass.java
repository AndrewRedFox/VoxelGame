package core.GameEngine.GameCore;

import jglm.Vec;

public class Mass {

    public float m; //значение массы
    public Vector3D pos; //положение в пространстве
    public Vector3D vel; //скорость
    public Vector3D force; //воздействующая сила

    public Mass(float m) {
        this.m = m;
    }

    public Mass() {
        m = 0;
    }

    public void applyForce(Vector3D force) { // Внешнюю силу прибавляем к «нашей»
        this.force.plusAndEqualsOperator(force);
    }

    public void init() { //обнуление силы
        force.x = 0;
        force.y = 0;
        force.z = 0;
    }

    public void simulate(float dt){ //dt это время
        vel.plusAndEqualsOperator((force.divideOperator(m)).multiplyOperator(dt)); //изменение скорости
        pos.plusAndEqualsOperator(vel.multiplyOperator(dt)); //изменение положения
    }





}
