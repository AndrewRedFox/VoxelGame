package input;

import Renderer.GraphicsDisplay;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Keyboard {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

    public static boolean keyDown(int keyId) {//врнет тру если мы нажмем на клавишу
        return GLFW.glfwGetKey(GraphicsDisplay.getWindow().getThisWindow(), keyId) == GLFW_PRESS;
    }

    public static boolean keyPressed(int keyId) {
        return keyDown(keyId) && !keys[keyId];
    }

    public static boolean keyReleased(int keyId) {
        return !keyDown(keyId) && keys[keyId];
    }

    public static void handleKeyboardInput() {
        for (int i = GLFW.GLFW_KEY_SPACE; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = keyDown(i);
        }
    }
}
