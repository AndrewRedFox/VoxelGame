package Renderer;

import static org.lwjgl.opengl.GL33.*;

public class VBO {
    private int id;
    private ArrayContainer arrayContainer;

    VBO(ArrayContainer arrayContainer) {
        id = glGenBuffers();
        this.arrayContainer = arrayContainer;
        //bind();
        //refresh();
    }

    void bindRefresh() {
        bind();
        refresh();
    }

    void refresh() {
        glBufferData(GL_ARRAY_BUFFER, arrayContainer.vertices, GL_STATIC_DRAW);
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
