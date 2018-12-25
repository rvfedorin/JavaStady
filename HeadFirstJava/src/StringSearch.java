public class StringSearch {
    static public void main (String[] args)
    {
        String[] example_text = {"Java string", "XML string", "html string",
                                 "cpp string", "python string", "NOT fail",
                                };
        int counter1 = 0, counter2 = 0, counter3 = 0;

        for (String oneString : example_text)
        {
            System.out.print(oneString.substring(0, 4).trim() + ' ');
            if (oneString.endsWith("string")) { counter1 ++; }
            if (oneString.startsWith("Java")) { counter2 ++; }
            if (oneString.contains("fail")) { counter3 ++; }
        }

        System.out.println("\ncounter1 = " + Integer.toString(counter1));
        System.out.println("counter2 = " + Integer.toString(counter2));
        System.out.println("counter3 = " + Integer.toString(counter3));

    }
}
