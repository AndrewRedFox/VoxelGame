package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Camera {
    public Vector3f position, orientation, up;
    public int width, height;
    public float speed = 0.1f, sensitivity = 100.0f;
    private static final float pi = 3.14159265359f;
    private boolean firstClick;

    Camera(int width, int height, Vector3f position){
        orientation = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);
        this.height = height;
        this.width = width;
        this.position = position;
        firstClick = true;
    }

    void Matrix(float FOVdeg, float nearPlane, float farPlane, Shader shader, String uniform){

        Matrix4f view = new Matrix4f();
        Vector3f center = new Vector3f().add(position).add(orientation);
        view = view.lookAt(position, center, up);
        Matrix4f proj = new Matrix4f();
        proj = proj.perspective(FOVdeg/180.0f*pi, (float) (width/height), nearPlane, farPlane );

        int projLoc = glGetUniformLocation(shader.getId(), uniform);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(projLoc, false, proj.mul(view).get(stack.mallocFloat(16)));
        } catch (Exception e) { System.out.println(e + " error in Camera"); }
    }

    void Inputs(long window){
        if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
            Vector3f delta = new Vector3f().add(orientation).mul(speed);
            position.add(delta);
            //System.out.println(orientation);
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
            Vector3f delta = new Vector3f().add(orientation).mul(-speed);
            position.add(delta);
        }
        if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
            Vector3f delta = new Vector3f().add(orientation).cross(up).normalize().mul(speed);
            position.add(delta);
        }
        if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
            Vector3f delta = new Vector3f().add(orientation).cross(up).normalize().mul(-speed);
            position.add(delta);
        }

        if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS){
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            if (firstClick){
                glfwSetCursorPos(window, width/2.0f, height/2.0f);
                firstClick = false;
            }
            double[] mouseX = {0.0}, mouseY = {0.0};
            glfwGetCursorPos(window, mouseX, mouseY);
            float rotX = sensitivity * (float) (mouseY[0] - height/2.0)/height;
            float rotY = sensitivity * (float) (mouseX[0] - width/2.0)/width;

            Vector3f newOrientation = new Vector3f();

            newOrientation.add(orientation).rotateX(-rotX/180.0f*pi).rotateY(-rotY/180.0f*pi);
            orientation = new Vector3f().add(newOrientation);

            glfwSetCursorPos(window, width/2.0f, height/2.0f);
        }
        if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE){
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            firstClick = true;
        }
    }
}
