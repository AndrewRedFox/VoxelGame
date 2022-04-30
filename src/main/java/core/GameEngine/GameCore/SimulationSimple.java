package core.GameEngine.GameCore;

import Renderer.VAO;
import core.GameEngine.Character.Character;
import core.GameEngine.MBO;

public class SimulationSimple {
    private Mass mass;
    private Vector3D gravitation = new Vector3D(0.0f, -9.8f, 0.0f);
    private Vector3D wind = new Vector3D(2.0f, 0.0f, 0.0f);
    private float dt = 0.013f;
    private MBOsObjects mbOsObjects;

    public SimulationSimple(float m, MBO object, MBOsObjects mbOsObjects) {
        this.mbOsObjects = mbOsObjects;
        this.mass = new Mass(m, object, mbOsObjects);
    }

    public void init() {
        mass.init();
    }

    public Mass getMass() {
        return mass;
    }

    public void setObject(MBO object, MBOsObjects mbOsObjects) {
        this.mass = new Mass(object, mbOsObjects);
    }

    public void solve() {//применяем силы
        //mass.applyForce(gravitation.multiplyOperator(mass.rigidBody.getMass()));
        //mass.applyForce(wind.multiplyOperator(mass.rigidBody.getMass()));
    }

    public void simulate(float dt) {
        mass.simulate(dt);
    }

    public Vector3D getObject() {
        return mass.getObj();
    }

    public void operate(MBO object) { // Полная процедура симуляции.
        mass.object = object;
        mass.collision.setObject(object);
        mass.rigidBody = object.getRigidBody();
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Процедура
    }

}
