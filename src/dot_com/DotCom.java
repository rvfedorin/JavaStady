package dot_com;

import java.util.ArrayList;

public class DotCom {
    private String name = "Noname";
    private ArrayList<String> location;


    public void setName(String newName)
    {
        name = newName;
    }

    public String getName()
    {
        return name;
    }

    public void SetLocationCells(ArrayList<String> newLocation)
    {
        location = newLocation;
    }

    public String checkYourSelf(String guess)
    {
        String result = "Мимо";

        if (location.contains(guess))
        {
            location.remove(location.indexOf(guess));
            result = "Попал";
        }

        if (location.isEmpty())
        {
            result = "Потопил";
        }

        return result;
    }

}

