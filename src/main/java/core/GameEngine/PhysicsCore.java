package core.GameEngine;

import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.MBOsObjects;
import core.GameEngine.GameCore.SimulationSimple;
import core.GameEngine.GameCore.Vector3D;

import java.util.HashSet;
import java.util.Random;

public class PhysicsCore {
    public MBOsObjects mbOsObjects = new MBOsObjects();
    public SimulationSimple simulate = new SimulationSimple(2.0f);//object
    public GraphicsDisplay graphicsDisplay;

    public PhysicsCore () {

    }


    public void setGraphicsDisplay(GraphicsDisplay graphicsDisplay){
        this.graphicsDisplay = graphicsDisplay;
    }

    public MBO[] getMas() {
        return mbOsObjects.getMBOs();
    }


    public void run() {
        Thread thread = new Thread(() -> {
            System.out.println("Start runSim");
            while (!graphicsDisplay.toClose) {
                simulate.setObject(mbOsObjects.getMBOs()[1]);//одновременно идет проверка коллизий и применение силы на обьект
                simulate.operate(mbOsObjects.getMBOs()[1]);
                mbOsObjects.getMBOs()[1].setVector3D(simulate.getObject());
                //System.out.println(object.getX() + " " + object.getY() + " " + object.getZ());
                try {
                    Thread.sleep(10l);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            System.out.println("End runSim");
        });
        thread.start();
        System.out.println("start gui");
        graphicsDisplay.run();
        System.out.println("close gui");
    }

}
