package core.GameEngine.Character;

import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.Mass;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;

public class SimulationCharacter {

    private CharactersMath charactersMath;
    private Vector3D gravitation = new Vector3D(0.0f, -9.8f, 0.0f);
    private float dt = 0.013f;
    private MBOsObjects mbOsObjects;

    public SimulationCharacter( Character character, MBOsObjects mbOsObjects) {
        this.mbOsObjects = mbOsObjects;
        this.charactersMath = new CharactersMath(character, mbOsObjects);
    }

    public void init() {
        charactersMath.init();
    }

    public CharactersMath getMass() {
        return charactersMath;
    }

    public void setObject(Character character, MBOsObjects mbOsObjects) {
        this.charactersMath = new CharactersMath(character, mbOsObjects);
    }


    public void solve() {//применяем силы
        charactersMath.applyForce(gravitation.multiplyOperator(charactersMath.rigidBody.getMass()));
    }

    public void simulate(float dt) {
        charactersMath.simulateForCharacter(dt);
    }

    public Vector3D getObject() {
        return charactersMath.getObj();
    }


    public void operate(Character object) { // Полная процедура симуляции.
        charactersMath.setCharacter(object);
        charactersMath.getCollision().setObject(object);
        charactersMath.rigidBody = object.getRigidBody();
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Процедура
    }

}
