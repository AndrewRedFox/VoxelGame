package core.GameEngine.GameCore;

import jglm.Vec;

public class ConstantVelocity extends Simulation {

    public ConstantVelocity() { //Масса с постоянной скоростью
        //super(1, 1.0f);
        masses[0].pos.equalsOperator(new Vector3D(0.0f, 0.0f, 0.0f)) ;
        masses[0].vel.equalsOperator(new Vector3D(1.0f, 0.0f, 0.0f)) ;
    }
}
