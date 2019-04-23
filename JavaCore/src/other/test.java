package other;


public class test {
    public static void main(String[] args) {
//        String data = "Orel-IPSidK74           in      5120    95.80.109.176           -unnumbered";
        String data = "Kr-KurskmetTel  in      1024    172.30.74.108/30";
        String[] cl = data.split("\\s+");

        if(data.contains("/"))
            System.out.println("True");
        String[] ip = cl[3].split("/\\d{2}");
        for (String s: ip) {
            System.out.println(s);
        }
        System.out.println(ip.length);
    }
}

// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////