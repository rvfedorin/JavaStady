public class chap4 {
    static public void main(String[] args)
    {
        String[] myString= {"a", "b", "c"};

        int input_true = 3;
        String input_str = "2";

        try
        {
            System.out.println(myString[input_true]);
            System.out.println(myString[Integer.parseInt(input_str)]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ошибка ввода.");
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Нет такого индекса.\n" + e);
        }

        for (int i = 0; i < myString.length; i++)
        {
            System.out.println(myString[i]);
        }

    }
}
