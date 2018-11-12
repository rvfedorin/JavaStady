public class StringSearch {
    static public void main (String[] args)
    {
        String[] example_text = {"Java string", "XML string", "html string",
                                 "cpp string", "python string", "NOT string",
                                };
        int counter1 = 0, counter2 = 0, counter3 = 0;

        for (int i = 0; i < example_text.length; i++)
        {
            System.out.print(example_text[i].substring(0, 4) + ' ');
        }

    }
}
