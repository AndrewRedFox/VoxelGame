package Renderer;

import static org.lwjgl.opengl.GL33.*;

//Шейдерная программв
public class Shader {
    private int id;//идентификатор программы

    //Конструктор
    Shader(String vertexString, String fragmentString) {
        //вершинный шейдер
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexString);
        glCompileShader(vertexShader);

        //фрагментный шейдер
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentString);
        glCompileShader(fragmentShader);

        id = glCreateProgram();
        glAttachShader(id, vertexShader);
        glAttachShader(id, fragmentShader);
        glLinkProgram(id);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    int getId() {
        return id;
    }

    void activate() {
        glUseProgram(id);//активация шейдерной программы
    }

    void delete() {
        glDeleteProgram(id);//удаление шейдерной программы
    }
}
