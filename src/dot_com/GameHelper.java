package dot_com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameHelper {
    private final String[] abc = {"a","b","c","d","e","f","g"};
    private final String[] numeric = {"1","2","3","4","5","6","7"};
    private String used = "";

    public void clearUsed()
    {
        used = "";
    }

    public String getUserInput(String prompt)
    {
        String inputLine = null;
        System.out.print(prompt);

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0)
            {
                return null;
            }
        } catch (IOException e)
        {
            System.out.println("IOException: " + e);
        }

        return inputLine;
    }

    public ArrayList<String> placeDotCom(int numPosition)
    {
        int tooMuch = 0;
        int posCounter = numPosition;
        int direction = -1;
        String cell;
        String tempUsed = used;

        ArrayList<String> allPosition = new ArrayList<>();


//        int startPosition = ((int) (Math.random() * (abc.length-2)) + 1);

        int x = ((int) (Math.random() * (abc.length-2))) + 2; // Выибираем цифру
        int y = ((int) (Math.random() * (abc.length-2))) + 2; // Выибираем букву


        while (tooMuch++ < 200 && posCounter > 0)
        {
            if (direction == -1){
                direction = (int) (Math.random() * 3);
            }
            int tempX = x;
            int tempY = y;


            switch (direction)
            {
                case 0:
                    if (x > 0)
                    {
                        x -= 1;
                        break;
                    }
                    direction = 0;
                case 1:
                    if (y > 0)
                    {
                        y -= 1;
                        break;
                    }
                    direction = 1;
                case 2:
                    if (x < 6)
                    {
                        x += 1;
                        break;
                    }
                    direction = 2;
                case 3:
                    if (y < 6)
                    {
                        y += 1;
                        break;
                    }
            }

            String hor = numeric[x];
            String vert = abc[y];
            cell = vert + hor;
            if (used.contains(cell))  // Если такое полу уже есть, пробуем заново
            {
                x = tempX;  // возвращаем в положение до перехода
                y = tempY;
                if (direction < 3) {
                    direction += 1;
                } else {
                    direction = 0;
                }

            } else {
                used += cell;
                posCounter--;
                allPosition.add(String.valueOf(cell));
            }

            if ((posCounter == 0 || tooMuch >= 200) && (allPosition.size() != numPosition))
            {
//                System.out.println("IN   ->>>>>>>>>>>>");
                allPosition.clear();
                used = tempUsed;
                x = ((int) (Math.random() * (abc.length-2))) + 1; // Выибираем цифру
                y = ((int) (Math.random() * (abc.length-2))) + 1; // Выибираем букву
                posCounter = numPosition;
                tooMuch = 0;
            }

        } //while (searching && tooMuch++ < 200 && numPosition > 0)

//        System.out.println(allPosition.size());
//        System.out.println(posCounter);
//        System.out.println(tooMuch);
        return allPosition;
    }
}
