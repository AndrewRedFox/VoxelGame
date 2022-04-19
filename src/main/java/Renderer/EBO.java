package Renderer;

import static org.lwjgl.opengl.GL33.*;

public class EBO {
    private int id;
    private ArrayContainer arrayContainer;

    EBO(ArrayContainer arrayContainer) {
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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, arrayContainer.indices, GL_STATIC_DRAW);
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