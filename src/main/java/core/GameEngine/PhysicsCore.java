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
    public SimulationSimple[] simulations;//object
    private Launcher launcher;
    private long delay;

    private SimulationCharacter simulateCharacter;
    private Character character = new Character();

    private double lastTime = 0;
    private double updateTicks = 0;
    private final int threadCount = 2;

    public PhysicsCore(MBO[] mbos, long delay, Launcher launcher) {
        this.mbOsObjects = new MBOsObjects(mbos);
        this.delay = delay;
        this.launcher = launcher;
        this.simulations = new SimulationSimple[threadCount];
        for(int i = 0; i < threadCount; i++){
            simulations[i] = new SimulationSimple(2.0f, mbOsObjects.getByIndex(1), mbOsObjects);
        }
        this.simulateCharacter = new SimulationCharacter(character, mbOsObjects);
    }

    private void printCoreUpdateRate(){
        updateTicks++;
        double currentTime = System.currentTimeMillis()/1000.0;
        if (currentTime - lastTime > 2.0) {
            System.out.println("core\t" + updateTicks/2.0);
            lastTime = currentTime;
            updateTicks = 0;
        }
    }

    public void run() {

        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!launcher.toClose()) {
                long startTime = System.nanoTime();
                int auxSize = mbOsObjects.size()/threadCount;
                Thread[] threads = new Thread[threadCount];
                for (int i = 0; i < threadCount; i++) {
                    threads[i] = runAuxThread(i*auxSize, (i+1)*auxSize, simulations[i]);
                }
                for (Thread threadA: threads) {
                    try {
                        threadA.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                long endTime = System.nanoTime();
                if(endTime - startTime < 9000000L){
                    try {
                        Thread.sleep((9000000L - endTime + startTime)/1000000L);
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

    private Thread runAuxThread(int start, int end, SimulationSimple simulationSimple){
        Thread auxThread = new Thread(() -> {
            for (int i = start; i < Math.min(end, mbOsObjects.size()); i++) {
                MBO temp = mbOsObjects.getByIndex(i);
                synchronized (temp) {
                    simulationSimple.setObject(temp, mbOsObjects);
                    simulationSimple.operate(temp);
                    temp.setVector3D(simulationSimple.getObject());
                }
            }
        });
        auxThread.start();
        return auxThread;
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
