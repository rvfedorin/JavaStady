package dot_com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameHelper {
    private final String[] abc = {"a","b","c","d","e","f","g"};
    private final String[] numeric = {"1","2","3","4","5","6","7"};
    private String used = "";

    public void clearUsed() {
        used = "";
    }

    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.print(prompt);

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0) {
                return null;
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return inputLine;
    }

    // Выбор расположения корабля
    public ArrayList<String> placeDotCom(int numPosition) {
        int tooMuch = 0;  // количесвто проходов цикла
        int posCounter = numPosition;  // счетчик выбранных полей
        int direction = -1;  // направление расположения (-1 случайный выбор)
        String cell;  // название поля вида "с1"
        String tempUsed = used;  // использованные поля до изменений

        ArrayList<String> allPosition = new ArrayList<>();  // итоговывй результат "а1b1b2"


//        int startPosition = ((int) (Math.random() * (abc.length-2)) + 1);

        int x = ((int) (Math.random() * (numeric.length-2))) + 2; // Выибираем цифру
        int y = ((int) (Math.random() * (abc.length-2))) + 2; // Выибираем букву


        while (tooMuch++ < 200 && posCounter > 0) {
            // Если направление не фиксировано, выбираем случайное
            if (direction == -1) {
                direction = (int) (Math.random() * 3);
            }
            // сохраняем начальнык координаты, на случай, если придется вернуться к ним
            int tempX = x;
            int tempY = y;

            switch (direction) {
                case 0:  // влево
                    if (x > 0) { x -= 1; break; }
                    // если направление не подходит и мы не вышли, то идём далее
                    direction = 0; // переходим к следующему положению и задаём, что это пройдено
                case 1: // вверх
                    if (y > 0) { y -= 1; break; }
                    // если направление не подходит и мы не вышли, то идём далее
                    direction = 1;
                case 2: // вправо
                    if (x < numeric.length-1) { x += 1; break; }
                    // если направление не подходит и мы не вышли, то идём далее
                    direction = 2;
                case 3: // вниз
                    if (y < abc.length-1) { y += 1; break; }
            } // switch (direction)

            String hor = numeric[x];
            String vert = abc[y];
            cell = vert + hor;  // собираем строку вида "с1"

            // Если такое поле занято, пробуем заново
            if (used.contains(cell)) {
                // возвращаем в положение до перехода
                x = tempX;
                y = tempY;
                // Смотрим на каком направлении остановились и задаём следующее
                if (direction < 3) {
                    direction += 1;
                } else {
                    direction = 0;
                }

            } else {  // если поле свободно
                used += cell;  // добавляем новое поле в занятые
                posCounter--;  // уменьшаем количество требуемыъ полей
                allPosition.add(String.valueOf(cell));
                direction = -1;  // задаем случайный выбор поля на следующей итерации
            }

            // Если по попали в тупик
            if ((posCounter == 0 || tooMuch >= 200) && (allPosition.size() != numPosition)) {
//                System.out.println("IN   ->>>>>>>>>>>>");
                allPosition.clear();  // удаляем выбранные ранее поля
                used = tempUsed;  // очищаем занятые поля
                x = ((int) (Math.random() * (abc.length-2))) + 1; // Выибираем случайно цифру
                y = ((int) (Math.random() * (abc.length-2))) + 1; // Выибираем случайно букву
                posCounter = numPosition;  // сьрасываем счётчик не выбранных полей
                tooMuch = 0;  // сбрасываем счётчик проходов цикла
            }

        } //while (searching && tooMuch++ < 200 && numPosition > 0)

        return allPosition;
    }
}
