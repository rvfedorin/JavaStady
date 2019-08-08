package other;


public class test {
    public static void main(String[] args) {
        int x = 0;

        while (x < 4) {
            x = x + 1;
            if (x < 1) {
                System.out.print("an");
            }
            x = x - 1;

            if (x > 1) {
                System.out.println("an");
                x = x + 1;
            }

            if (x == 1) {
                System.out.print("noise");
            }

            if (x > 0) {
                System.out.println("234");
            }

            System.out.println();
            x = x - 2;
        }


    } // ** main()

} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////