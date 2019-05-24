package other;

import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        Pattern ERROR_PATTERN = Pattern.compile("Invalid|Error|Fail|exist|vlanid 2-4094");
        String s = "DGS-3420-28TC:admin#delete vlan Orel-test1\n" +
                "Command: delete vlan Orel-test1\n" +
                " The VLAN does not exi.\n" +
                "!          Fail         ";

        if(ERROR_PATTERN.matcher(s).find()) {
            System.out.println("ERROR!!!!");
        }
    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////