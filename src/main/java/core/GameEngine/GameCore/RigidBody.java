package core.GameEngine.GameCore;

public class RigidBody {
    private float mass;
    private float speed;
    private Vector3D speedV = new Vector3D();
    private float drag; // Насколько тело подвержено сопротивлению воздуха
    private boolean UseGravity;

    public RigidBody(float mass, float speed, float drag, boolean UseGravity) {
        this.mass = mass;
        this.speed = speed;
        this.drag = drag;
        this.UseGravity = UseGravity;
    }

    public RigidBody(float mass, Vector3D speed, float drag, boolean UseGravity) {
        this.mass = mass;
        this.speedV = speed;
        this.drag = drag;
        this.UseGravity = UseGravity;
    }

    public void adjustSpeed(float dx, float dy, float dz){
        speedV.x += dx;
        speedV.y += dy;
        speedV.z += dz;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector3D getSpeedV() {
        return speedV;
    }

    public void setSpeedV(Vector3D speedV) {
        this.speedV = speedV;
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
