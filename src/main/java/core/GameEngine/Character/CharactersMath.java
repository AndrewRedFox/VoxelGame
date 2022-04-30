package core.GameEngine.Character;

import core.GameEngine.GameCore.Collision.Collision;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.RigidBody;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;

public class CharactersMath {

    public RigidBody rigidBody;
    public Vector3D pos = new Vector3D(); //положение в пространстве
    public Vector3D vel = new Vector3D(); //скорость
    public Vector3D force = new Vector3D(); //воздействующая сила
    public Character character;
    public CollisionForCharacter collision;
    private Vector3D otherObjectPos;



    public CharactersMath(Character character, MBOsObjects mbOsObjects) {
        this.character = character;
        rigidBody = this.character.getRigidBody();
        this.collision = new CollisionForCharacter(character, mbOsObjects);
        vel = rigidBody.getSpeedV();
    }

    public void applyForce(Vector3D force) { // Внешнюю силу прибавляем к «нашей»
        this.force.plusAndEqualsOperator(force);
    }


    public void rePosition() {
        this.pos.change(character.getVector3D());
    }


    public void init() { //обнуление силы
        force.x = 0;
        force.y = 0;
        force.z = 0;

    }

    public Vector3D getObj() {
        return pos;
    }

    public void simulateForCharacter(float dt) { //dt это время
        rePosition();
        if (!collision.isCollision()) {
            vel.plusAndEqualsOperator((force.divideOperator(rigidBody.getMass())).multiplyOperator(dt)); //изменение скорости
            rigidBody.setSpeedV(vel);
            pos.plusAndEqualsOperator(vel.multiplyOperator(dt)); //изменение положения
            character.setVector3D(pos);
        }
    }
}
