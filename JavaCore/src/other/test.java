package other;

public class test {
    public static void main(String[] args) {

        String ip = " <=>  [28] 172.16.48.254(switch) [9] <=> [25] 172.16.48.42(switch) [3] <=> [9] 172.16.49.134(switch)  [3]";
        ip = ip.replaceAll("\\(.*?\\)|]|\\[", "");
        System.out.println(ip);
        int i = 0;
        ip = ip.replaceFirst(" <=> ", "");
        for(String node: ip.split("<=>")){
            System.out.println(node.replaceAll("\\s*", ""));
            System.out.println(i++);
        }


    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////