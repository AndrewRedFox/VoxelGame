package core.GameEngine.GameCore;

public class RigidBody {
    private float mass;
    private float speed;
    private float drag; // Насколько тело подвержено сопротивлению воздуха
    private boolean UseGravity;

    public RigidBody(float mass, float speed, float drag, boolean UseGravity) {
        this.mass = mass;
        this.speed = speed;
        this.drag = drag;
        this.UseGravity = UseGravity;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDrag() {
        return drag;
    }

    public void setDrag(float drag) {
        this.drag = drag;
    }

    public boolean isUseGravity() {
        return UseGravity;
    }

    public void setUseGravity(boolean useGravity) {
        UseGravity = useGravity;
    }
}
