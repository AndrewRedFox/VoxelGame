package Renderer;

import static org.lwjgl.opengl.GL33.*;

//Element buffer object, используется для хранения индексов вершин
public class EBO {
    private int id; //идентификатор EBO
    private ArrayContainer arrayContainer; //ссылка на ArrayContainer, откуда берётся информация

    //Конструктор
    EBO(ArrayContainer arrayContainer) {
        id = glGenBuffers();// генерация id буфера
        this.arrayContainer = arrayContainer;
    }

    void bindRefresh() {
        bind();//привязка
        refresh();//обновление
    }

    void refresh() {
        //Создание, инициализация и заполнение буферного хранилища
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, arrayContainer.indices, GL_STATIC_DRAW);
    }

    void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);//привязка EBO
    }

    void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);//отвязка EBO(через задание индекса привязываемого буфера равным 0)
    }

    void delete() {
        glDeleteBuffers(id);//удаление EBO
    }
}