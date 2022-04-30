package core.GameEngine;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.SimulationImpuls;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;
import core.Launcher;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class PhysicsCore {
    public MBOsObjects mbOsObjects;
    public SimulationSimple simulate;//object
    private Launcher launcher;
    private long delay;
    //public SimulationImpuls simulationImpuls;

    public PhysicsCore(MBO[] mbos, long delay, Launcher launcher) {
        this.mbOsObjects = new MBOsObjects(mbos);
        this.delay = delay;
        this.launcher = launcher;
        this.simulate = new SimulationSimple(2.0f, mbOsObjects.getByIndex(1), mbOsObjects);
    }

    public void run() {

        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!launcher.toClose()) {
                synchronized (this) {
                    for (int i = 0; i < mbOsObjects.size(); i++) {
                        MBO temp = mbOsObjects.getByIndex(i);
                        this.simulate.setObject(temp, mbOsObjects);
                        this.simulate.operate(temp);
                        temp.setVector3D(simulate.getObject());
                    }
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            System.out.println("End runSim");
        });
        thread.start();
    }

    public void Inputs(long window){
        float delta = 0.05f;
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(-delta,0f,0f);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(delta,0f,0f);
        }
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f,delta,0f);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f,-delta,0f);
        }
        if (glfwGetKey(window, GLFW_KEY_PAGE_UP) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f,0f,delta);
        }
        if (glfwGetKey(window, GLFW_KEY_PAGE_DOWN) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f,0f,-delta);
        }
    }

}
