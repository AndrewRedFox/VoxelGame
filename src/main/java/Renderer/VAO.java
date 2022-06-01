package Renderer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

//Vertex Array Object, используется для инициализации хранения информации верщин
public class VAO {
    private int id;//id VAO

    //Конструктор
    VAO() {
        id = glGenVertexArrays();// генерация id VAO
    }


    void LinkAttrib(VBO vbo, int layout, int offset, int pointer) {
        //инициализация VAO с помощью VBO
        vbo.bind();
        glVertexAttribPointer(layout, GraphicsDisplay.vertexSize, GL_FLOAT, false, offset, pointer);
        glEnableVertexAttribArray(layout);
        vbo.unbind();
    }

    void bind() {
        glBindVertexArray(id);//привязка VAO
    }

    void unbind() {
        glBindVertexArray(0);//отвязка VAO(через задание индекса привязываемого буфера равным 0)
    }

    void delete() {
        glDeleteVertexArrays(id);//Удаление VAO
    }
}
