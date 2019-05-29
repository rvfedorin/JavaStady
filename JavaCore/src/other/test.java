package other;

public class test {
    public static void main(String[] args) {

        String ip = "[28] 172.16.48.254(switch) [9] <=> [25] 172.16.48.42(switch) [3] <=> [9] 172.16.49.134(switch)";
        ip = ip.replaceAll("\\(.*?\\)", "");
        System.out.println(ip);


    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////