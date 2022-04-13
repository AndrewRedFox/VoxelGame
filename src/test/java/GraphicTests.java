import Renderer.GraphicsDisplay;
import core.GameEngine.GameCore.Vector3D;
import core.GameEngine.MBO;
import core.Launcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class GraphicTests {
    @Test
    void autoTest() {
        MBO[] mbos = {
                new MBO(MBO.genVoxelArray(50000), new Vector3D(0.0f, 1.0f, 0.0f), 0.0f, 0.0f, 0.0f),
        };
        MBO object = new MBO(
                MBO.genVoxelArray(2),
                new Vector3D(0.0f, 0.0f, -10.0f), 0.0f, 0.0f, 0.0f
        );
        Launcher launcher = new Launcher(mbos, object);
        launcher.run();
    }
}
