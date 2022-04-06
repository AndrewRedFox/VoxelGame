package Renderer;

import static org.lwjgl.opengl.GL33.*;

public class VBO {
    private int id;
    private int verticesSize;
    VBO(float[] vertices){
        id = glGenBuffers();
        verticesSize = vertices.length / 5;
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }
    int getVerticesSize() { return verticesSize; }
    void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }
    void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    void delete(){
        glDeleteBuffers(id);
    }
}
