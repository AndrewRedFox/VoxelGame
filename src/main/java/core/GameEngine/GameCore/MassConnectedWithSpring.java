package core.GameEngine.GameCore;

public class MassConnectedWithSpring extends Simulation { // устанавливает точку присоединения и положение массы

    public float springConstant; // больше springConstant, сильнее сила притяжения
    public Vector3D connectionPos; // Точка

    public MassConnectedWithSpring(float springConstant) {
        super(1, 1.0f);
        this.springConstant = springConstant;
        connectionPos.equalsOperator(new Vector3D(0.0f, -5.0f, 0.0f));

        masses[0].pos = connectionPos.plusOperator(new Vector3D(10.0f, 0.0f, 0.0f));
        masses[0].vel = new Vector3D(0.0f, 0.0f, 0.0f);
    }

    public void solve() { //применяем силу пружины

        for (int i = 0; i < numOfMasses; ++i) {
            Vector3D springVector = masses[i].pos;
            masses[i].applyForce(springVector.multiplyOperator(springConstant).multiplyOperator(-1.0f));
        }
    }

}
