public class StringSearch {
    static public void main (String[] args)
    {
        String[] example_text = {"Java string", "XML string", "html string",
                                 "cpp string", "python string", "NOT fail",
                                };
        int counter1 = 0, counter2 = 0, counter3 = 0;

        for (int i = 0; i < example_text.length; i++)
        {
            System.out.print(example_text[i].substring(0, 4) + ' ');
            if (example_text[i].endsWith("string")) { counter1 ++; }
            if (example_text[i].startsWith("Java")) { counter2 ++; }
            if (example_text[i].indexOf("fail") == -1) {counter3 ++; }
        }

        System.out.println("\ncounter1 = " + Integer.toString(counter1));
        System.out.println("counter2 = " + Integer.toString(counter2));
        System.out.println("counter3 = " + Integer.toString(counter3));

    }
}
