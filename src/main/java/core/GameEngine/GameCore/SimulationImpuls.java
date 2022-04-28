package core.GameEngine.GameCore;

import core.GameEngine.MBO;

public class SimulationImpuls {
    private Mass mass;
    private float dt = 0.013f;
    private Vector3D speed = new Vector3D(0.0f,0.0f,0.0f);
    private MBOsObjects mbOsObjects;

    public SimulationImpuls(float m, MBO object, MBOsObjects mbOsObjects){
        this.mbOsObjects = mbOsObjects;
        this.mass = new Mass(m, object, mbOsObjects);
        speed.changeX(mass.rigidBody.getSpeed());
    }

    public void init() {
        mass.init();
    }

    public void setObject(MBO object) {
        mass.object = object;
    }

    public void solve() {//применяем силы
        mass.applyForce(speed.multiplyOperator(mass.rigidBody.getMass()));
    }

    public void simulate(float dt) {
        mass.simulate(dt);
    }

    public Vector3D getObject() {
        return mass.getObj();
    }

    public void operate(MBO object) { // Полная процедура симуляции.
        mass.object = object;
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Процедура
    }




}
