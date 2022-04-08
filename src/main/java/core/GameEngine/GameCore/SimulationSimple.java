package core.GameEngine.GameCore;

import Renderer.VAO;
import core.GameEngine.MBO;

public class SimulationSimple {
    public Mass mass = new Mass();
    public Vector3D gravitation = new Vector3D(0.0f, -9.8f, 0.0f);
    public Vector3D wind = new Vector3D(10.0f,0.0f,0.0f);
    public float dt = 0.013f;

    public SimulationSimple(float m, MBO object) {
        mass.m = m;
        mass.object = object;
    }

    public void init() {
        mass.init();
    }

    public void setObject(MBO object) {
        mass.object = object;
    }

    public void solve() {//применяем силы
        mass.applyForce(gravitation.multiplyOperator(mass.m));
        mass.applyForce(wind.multiplyOperator(mass.m));
    }

    public void simulate(float dt) {
        mass.simulate(dt);
    }

    public Vector3D getObject() {
        return mass.getObj();
    }

    public void operate() { // Полная процедура симуляции.
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Процедура
    }

}
