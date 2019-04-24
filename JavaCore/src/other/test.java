package other;


public class test {
    public static void main(String[] args) {
        String data = "95.80.127.253(<\\/th> <td>Cisco ASR1001 <\\/td> ) [0] <=> [1] 172.17.199.254(<\\/th> <td>D-Link DGS-3120-24TC<) [22] <=> [10] 172.17.199.250(<\\/th> <td>D-Link DES-3200-10 Fa) [2] <=>  172.17.196.2(DGSSkyMAN R5000-Omxb\\\" >\\n         ) [0] <=> [0] 172.17.196.78(<\\/th> <td>SkyMAN R5000-Sm\\/5.30) [0] <=> \n";
        data = data.replaceAll("<\\\\/th>|<\\\\/td>|\\\\n|<td>|\\n|\\\\\"|\\s+", "");
        System.out.println(data);
    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////