package core.GameEngine.GameCore;

public class Simulation { //massive для симуляции масс
    //Класс Simulation хранит массы как свои переменные. Задача этого класса создавать/удалять массы и проводить симуляцию.
    public int numOfMasses; //количество масс в массиве
    public Mass[] masses;

    // Конструктор создает numOfMasses масс с массой m.
    public Simulation(int numOfMasses, float m) {
        this.numOfMasses = numOfMasses;
        masses[numOfMasses] = new Mass();

        for (int i = 0; i < numOfMasses; ++i) {
            masses[i] = new Mass(m);
        }
    }

    public void release() { //чистим массив масс
        for (int i = 0; i < numOfMasses; ++i) {
            masses[i] = null;
        }
        masses = null;
    }

    public Mass getMass(int index) {
        if (index < 0 || index >= numOfMasses) return null;
        return masses[index];
    }

    public void init() {// вызываем init() для каждой массы
        for (int i = 0; i < numOfMasses; ++i) {
            masses[i].init();
        }
    }

    public void solve() {//применяем силы
        // Нет кода т.к. в базовом классе у нас нет сил
        // В других контейнерах мы переопределим этот метод
    }

    public void simulate(float dt) { // Итерация для каждой массы
        for (int i = 0; i < numOfMasses; ++i) {
            masses[i].simulate(dt);
        }
    }

    public void operate(float dt) { // Полная процедура симуляции.
        init();            // 1. Силу в 0
        solve();           // 2. Применяем силы
        simulate(dt);      // 3. Итерация
    }

}
