package Renderer;

import static org.lwjgl.opengl.GL33.*;

public class EBO {
    private int id;

    EBO(int[] indices) {
        id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    void delete() {
        glDeleteBuffers(id);
    }
}