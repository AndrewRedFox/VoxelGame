package Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.glGetUniformLocation;
import static org.lwjgl.opengl.GL33.glUniformMatrix4fv;

//Камера
public class Camera {
    private final Vector3f up;//вектор, направленный вверх
    public Vector3f position, orientation;//позиция, ориентация камеры
    public final int width, height;//ширина и высота отображаемой картинки
    public float speed = 0.1f, sensitivity = 100.0f;//скорость и чувствительность(мыши)
    private static final float pi = 3.14159265359f;
    private boolean firstClick;
    private final GraphicsDisplay graphicsDisplay;//ссылка на GraphicsDisplay

    //Конструктор
    Camera(int width, int height, Vector3f position, GraphicsDisplay graphicsDisplay) {
        orientation = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);
        this.height = height;
        this.width = width;
        this.position = position;
        this.graphicsDisplay = graphicsDisplay;
        firstClick = true;
    }

    //Вычисление матрицы и передача результата в шейдерную программу
    void Matrix(float FOVdeg, float nearPlane, float farPlane, Shader shader, String uniform) {

        Matrix4f view = new Matrix4f();
        Vector3f center = new Vector3f().add(position).add(orientation);
        view = view.lookAt(position, center, up);
        Matrix4f proj = new Matrix4f();
        proj = proj.perspective(FOVdeg / 180.0f * pi, (float) (width / height), nearPlane, farPlane);

        int projLoc = glGetUniformLocation(shader.getId(), uniform);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(projLoc, false, proj.mul(view).get(stack.mallocFloat(16)));
        } catch (Exception e) {
            System.out.println(e + " error in Camera: can't correctly send uniform data");
        }
    }

    //Чтение ввода
    void Inputs(long window) {
        if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
            graphicsDisplay.toClose = true;
            glfwSetWindowShouldClose(window, true);
        }

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            position.add(new Vector3f(orientation).mul(speed));
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            position.add(new Vector3f(orientation).mul(-speed));
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            position.add(new Vector3f(orientation).cross(up).normalize().mul(speed));
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            position.add(new Vector3f(orientation).cross(up).normalize().mul(-speed));
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            speed = 1.0f;
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_RELEASE) {
            speed = 0.1f;
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            speed = 0.025f;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            if (firstClick) {
                glfwSetCursorPos(window, width / 2.0f, height / 2.0f);
                firstClick = false;
            }
            double[] mouseX = {0.0}, mouseY = {0.0};
            glfwGetCursorPos(window, mouseX, mouseY);
            float rotX = sensitivity * (float) (mouseY[0] - height / 2.0) / height;
            float rotY = sensitivity * (float) (mouseX[0] - width / 2.0) / width;

            Vector3f newOrientation = new Vector3f(orientation);


            newOrientation.rotateY(-rotY / 180.0f * pi);
            newOrientation.rotateX((float) (rotX / 180.0f * Math.sin(orientation.z * pi / 2.0) * pi));
            newOrientation.rotateZ((float) (-rotX / 180.0f * Math.sin(orientation.x * pi / 2.0) * pi));

            if (newOrientation.angle(up) > 5.0 / 180.0 * pi && newOrientation.angle(up) < 175.0 / 180.0 * pi) {
                orientation = new Vector3f(newOrientation);
            }

            glfwSetCursorPos(window, width / 2.0f, height / 2.0f);
        }
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            firstClick = true;
        }
    }
}
