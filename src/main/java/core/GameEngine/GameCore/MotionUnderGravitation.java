package core.GameEngine.GameCore;

public class MotionUnderGravitation extends Simulation { // создает массу и применяет силу (гравитации) к ней

    Vector3D gravitaion; //ускорение свободного падения

    public MotionUnderGravitation(Vector3D gravitaion) {
        super(1, 1.0f);
        this.gravitaion = gravitaion;
        masses[0].pos = new Vector3D(-10.0f, 0.0f, 0.0f);
        masses[0].vel = new Vector3D(10.0f, 15.0f, 0.0f);
    }

    public void solve() {
        for (int i = 0; i < numOfMasses; ++i) {
            masses[i].applyForce(gravitaion.multiplyOperator(masses[i].m));
        }
    }


}
