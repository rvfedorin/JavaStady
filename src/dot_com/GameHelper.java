package dot_com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameHelper {
    private final String abc = "abcdefg";
    private final String numeric = "1234567";
    private String used = "";

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
        boolean searching = true;
        int tooMuch = 0;
        String cell;
        ArrayList<String> allPosition = new ArrayList<>();
        int startPosition = (int) (Math.random() * (abc.length()-1));

        int x = startPosition;  // Выибираем цифру
        int y = startPosition;  // Выибираем букву


        while (searching && tooMuch++ < 200 && numPosition > 0)
        {
            int direction = (int) (Math.random() * 3);

            switch (direction)
            {
                case 0:
                    if (x > 0 && y < 7)
                    {
                        String hor = numeric.substring(x-1, x);
                        String vert = abc.substring(y, y+1);
                        cell = vert + hor;
                        if (used.contains(cell))  // Если такое полу уже есть, пробуем заново
                        {continue;}

                        used += cell;
                        numPosition--;
                        x -= 1;
                        allPosition.add(String.valueOf(cell));
                    }
                    break;
                case 1:
                    if (y > 0 && x < 7)
                    {
                        String hor = numeric.substring(x, x+1);
                        String vert = abc.substring(y-1, y);
                        cell = vert + hor;
                        if (used.contains(cell))  // Если такое полу уже есть, пробуем заново
                        {continue;}
                        used += cell;
                        numPosition--;
                        y -= 1;
                        allPosition.add(String.valueOf(cell));
                    }
                    break;
                case 2:
                    if (x < 6 && y < 6)
                    {
                        String hor = numeric.substring(x, x+1);
                        String vert = abc.substring(y, y+1);
                        cell = vert + hor;
                        if (used.contains(cell))  // Если такое полу уже есть, пробуем заново
                        {continue;}
                        used += cell;
                        numPosition--;
                        x += 1;
                        allPosition.add(String.valueOf(cell));
                    }
                    break;
                case 3:
                    if (x < 6 && y < 6)
                    {
                        String hor = numeric.substring(x, x+1);
                        String vert = abc.substring(y, y+1);
                        cell = vert + hor;
                        if (used.contains(cell))  // Если такое полу уже есть, пробуем заново
                        {continue;}
                        used += cell;
                        numPosition--;
                        y += 1;
                        allPosition.add(String.valueOf(cell));
                    }
                    break;

            }
        }

        return allPosition;
    }
}
