package Renderer;

import static org.lwjgl.opengl.GL33.*;

public class VBO {
    private int id;

    VBO(float[] vertices) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    void delete() {
        glDeleteBuffers(id);
    }
}
