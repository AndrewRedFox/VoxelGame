package Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIVector2D;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46C.*;

public class Loader {
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public RawModel loadToVao(float[] positions, int[] indices) {
        int vaoId = createVao();
        bindIndicesBuffer(indices);
        storeDataInAttribute(0, positions);
        unbindVao();
        return new RawModel(vaoId, indices.length);
    }


    public void cleanUp() {
        for (int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
    }

    private int createVao() {
        int vaoId = glGenVertexArrays();
        vaos.add(vaoId);
        glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeDataInAttribute(int attributeNumber, float[] data) {
        int vboId = glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbindVao() {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboId= glGenBuffers();
        vbos.add(vboId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
