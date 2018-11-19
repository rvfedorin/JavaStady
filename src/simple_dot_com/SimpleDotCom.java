package simple_dot_com;

import java.util.ArrayList;

public class SimpleDotCom {
    private ArrayList<Integer> locationCells = new ArrayList<>();

    public String CheckYourSelf(String guess)
    {
        int shut = Integer.parseInt(guess);
        String result = "Мимо";

        if (locationCells.contains(shut))
        {
            locationCells.remove(locationCells.indexOf(shut));
            result = "Попал";
        }

        if (locationCells.isEmpty())
        {
            result = "Потопил";
        }

        return result;
    }

    public void SetLocation(int[] location)
    {
        for (int cell : location)
        {
            locationCells.add(cell);
        }

    }
}
