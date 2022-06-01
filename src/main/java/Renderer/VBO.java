package Renderer;

import static org.lwjgl.opengl.GL33.*;

//Vertex Buffer Object, используется для хранения информации вершин
public class VBO {
    private int id; //идентификатор VBO
    private ArrayContainer arrayContainer; //ссылка на ArrayContainer, откуда берётся информация

    //Конструктор
    VBO(ArrayContainer arrayContainer) {
        id = glGenBuffers();// генерация id буфера
        this.arrayContainer = arrayContainer;
    }

    void bindRefresh() {
        bind();//привязка
        refresh();//обновление
    }

    void refresh() {
        //обновление информации вершин
        glBufferData(GL_ARRAY_BUFFER, arrayContainer.vertices, GL_STATIC_DRAW);
    }

    void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);//привязка VBO
    }

    void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);//отвязка VBO(через задание индекса привязываемого буфера равным 0)
    }

    void delete() {
        glDeleteBuffers(id);//удаление VBO
    }
}
