package core.GameEngine;

import core.GameEngine.Character.Character;
import core.GameEngine.Character.SimulationCharacter;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.SimulationSimple;
import core.Launcher;

import java.util.Timer;

import static org.lwjgl.glfw.GLFW.*;

public class PhysicsCore {
    public MBOsObjects mbOsObjects;
    public SimulationSimple simulate;//object
    private Launcher launcher;
    private long delay;

    private SimulationCharacter simulateCharacter;
    private Character character = new Character();

    private double lastTime = 0;
    private int updateTicks = 0;

    public PhysicsCore(MBO[] mbos, long delay, Launcher launcher) {
        this.mbOsObjects = new MBOsObjects(mbos);
        this.delay = delay;
        this.launcher = launcher;
        this.simulate = new SimulationSimple(2.0f, mbOsObjects.getByIndex(1), mbOsObjects);
        this.simulateCharacter = new SimulationCharacter(character, mbOsObjects);
    }

    private void printCoreUpdateRate(){
        updateTicks++;
        double currentTime = System.currentTimeMillis()/1000.0;
        if (currentTime - lastTime > 5.0) {
            System.out.println("core\t" + updateTicks/5);
            lastTime = currentTime;
            updateTicks = 0;
        }
    }

    public void run() {

        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!launcher.toClose()) {
                long startTime = System.nanoTime();
                for (int i = 0; i < mbOsObjects.size(); i++) {
                    MBO temp = mbOsObjects.getByIndex(i);
                    synchronized (temp) {
                        this.simulate.setObject(temp, mbOsObjects);
                        this.simulate.operate(temp);
                        temp.setVector3D(simulate.getObject());
                    }
                }
                long endTime = System.nanoTime();
                if(endTime - startTime < 10000000L){
                    try {
                        Thread.sleep((10000000L - endTime + startTime)/1000000L);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                printCoreUpdateRate();
            }
            System.out.println("End runSim");
        });
        thread.start();
    }

    public void Inputs(long window) {
        float delta = 0.05f;

        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(-delta, 0f, 0f);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(delta, 0f, 0f);
        }
        /*if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f, delta, 0f);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f, -delta, 0f);
        }*/
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f, 0f, -delta);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f, 0f, delta);
        }
    }

    public void runCharacter() {
        Thread thread = new Thread(() -> {
            //System.out.println("Start runSim");
            while (!launcher.toClose()) {
                synchronized (this) {
                    Character temp = character;
                    this.simulateCharacter.setObject(temp, mbOsObjects);
                    this.simulateCharacter.operate(temp);
                    temp.setVectorPos(simulateCharacter.getObject());
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //System.out.println("End runSim");
        });
        thread.start();
    }

    public void InputsForCharacter(long window) {
        float delta = character.getSpeed();//было 0.05f
        Timer timer;

        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            character.adjustPos(-delta, 0f, 0f);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            character.adjustPos(delta, 0f, 0f);
        }
        if (glfwGetKey(window, GLFW_KEY_PAGE_UP) == GLFW_PRESS) {
            MBO mbo = mbOsObjects.getByIndex(0);
            mbo.getRigidBody().adjustSpeed(0f, 0f, delta);
            character.adjustPos(0f, 0f, delta);
        }
        if (glfwGetKey(window, GLFW_KEY_PAGE_DOWN) == GLFW_PRESS) {
            character.adjustPos(0f, 0f, -delta);
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_REPEAT) {
            character.adjustPos(0f, 0.3f, 0f);
        }

    }

}
