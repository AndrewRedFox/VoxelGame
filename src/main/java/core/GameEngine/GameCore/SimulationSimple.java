package core.GameEngine.GameCore;

import Renderer.VAO;
import core.GameEngine.MBO;

public class SimulationSimple {
    private Mass mass;
    private Vector3D gravitation = new Vector3D(0.0f, -9.8f, 0.0f);
    private Vector3D wind = new Vector3D(10.0f,0.0f,0.0f);
    private float dt = 0.013f;
    private MBOsObjects mbOsObjects;

    public SimulationSimple(float m, MBO object, MBOsObjects mbOsObjects) {
        this.mbOsObjects = mbOsObjects;
        this.mass = new Mass(m, object, mbOsObjects);
    }

    /*public SimulationSimple(float m) {
        mass.m = m;
    }*/

    public void init() {
        mass.init();
    }

    public void setObject(MBO object) {
        mass.object = object;
    }

    public void solve() {//применяем силы
        mass.applyForce(gravitation.multiplyOperator(mass.m));
        //mass.applyForce(wind.multiplyOperator(mass.m));
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
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Процедура
    }

}
