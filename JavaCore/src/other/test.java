package other;

public class test {
    public static void main(String[] args) {
        String s = "Command: show ports 23\n" +
                "line -> \n" +
                "line -> \n" +
                "line ->  Port      Port            Settings             Connection          Address \n" +
                "line -> \n" +
                "line ->            State     Speed/Duplex/FlowCtrl  Speed/Duplex/FlowCtrl   Learning\n" +
                "line -> \n" +
                "line ->  -------  --------  ---------------------  ----------------------  ---------\n" +
                "line -> \n" +
                "line ->  23   (C) Enabled   Auto/Disabled           Link Down               Enabled     \n" +
                "line -> \n" +
                "line ->  23   (F) Enabled   Auto/Disabled           1000M/Full/None         Enabled     ";

        StringBuilder result = new StringBuilder("\n");
        for(String line: s.split("\n")) {
            line = line.trim();
            if(line.length() > 0 && !line.contains("#")) {
                result.append("\t").append(line).append("\n");
                continue;
            }
            if(line.split("#").length < 2) {
                continue;
            }
            result.append(line).append("\n");
        } // ** for every line
        System.out.println(result.toString());

    } // ** main()
} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////