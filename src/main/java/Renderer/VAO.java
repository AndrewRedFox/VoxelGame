package Renderer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VAO {
    private int id;
    VAO(){
        id = glGenVertexArrays();
    }
    void LinkAttrib(VBO vbo, int layout, int offset, int pointer){
        vbo.bind();
        glVertexAttribPointer(layout, vbo.getVerticesSize(), GL_FLOAT, false, offset, pointer);
        glEnableVertexAttribArray(layout);
        vbo.unbind();
    }
    void bind(){
        glBindVertexArray(id);
    }
    void unbind(){
        glBindVertexArray(0);
    }
    void delete(){
        glDeleteVertexArrays(id);
    }
}
