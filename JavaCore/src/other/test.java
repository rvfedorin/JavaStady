package other;


public class test {
    public static void main(String[] args) {

        String[] cellConnect = new String[]{"1", " connect ", "3"};

        long start = System.currentTimeMillis();
        String node = "[" + cellConnect[0] + "]" + cellConnect[1] + "[" + cellConnect[2] + "] <=>";
        System.out.println("One: " + (System.currentTimeMillis() - start));
        System.out.println(node);

        start = System.currentTimeMillis();
        String node2 = String.format("[%s]%s[%s] <=>", cellConnect[0], cellConnect[1], cellConnect[2]);
        System.out.println(node2);
        System.out.println("Two: " + (System.currentTimeMillis() - start));
    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////