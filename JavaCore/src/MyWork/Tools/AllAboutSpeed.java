package MyWork.Tools;

public class AllAboutSpeed {
    private static final int KB = 1024;

    public static String[] getFormattedSpeed(String type, String speed) {
        String[] result = new String[2];
        String speedLowCase = speed.toLowerCase();
        int speedSize;
        try {
            speedSize = Integer.valueOf(speedLowCase.split("\\D")[0]);
        } catch (Exception ex) {
            result[0] = "[!!!] Error. ";
            result[1] = "\tFailed split speed.";
            return result;
        }

        if(type.equals("service-policy")) {
            if(speedLowCase.contains("m")) {
                int speedResult = speedSize * KB;
                result[0] = "service-policy input " + speedResult + "k";
                result[1] = "service-policy output " + speedResult + "k";

            } else if (speedLowCase.contains("k")) {
                result[0] = "service-policy input " + speedLowCase;
                result[1] = "service-policy output " + speedLowCase;

            } else {
                result[0] = "[!!!] Error. ";
                result[1] = "\tNot found speed size.";
            }

        } else if (type.equals("rate-limit")) {
            if(speedLowCase.contains("m")) {
                String spBlock = 1024000*speedSize + " " +
                                 192000*speedSize + " " +
                                 384000*speedSize + " ";

                result[0] = "rate-limit input " +
                        spBlock +
                        "conform-action transmit exceed-action drop";
                result[1] = "rate-limit output " +
                        spBlock +
                        "conform-action transmit exceed-action drop";

            } else if (speedLowCase.contains("k")) {
                int divider = KB / speedSize;
                String spBlock = (int)(1024000/divider) + " " +
                                 (int)(192000/divider) + " " +
                                 (int)(384000/divider) + " ";

                result[0] = "rate-limit input " +
                        spBlock +
                        "conform-action transmit exceed-action drop";
                result[1] = "rate-limit output " +
                        spBlock +
                        "conform-action transmit exceed-action drop";

            } else {
                result[0] = "[!!!] Error. ";
                result[1] = "\tNot found speed size.";
            }

        } else {
            result[0] = "[!!!] Error. ";
            result[1] = "\tNot found speed type: <<" + type + ">>";
        } // ** if(type)
        return result;
    } // ** getFormattedSpeed(String type)

    public String getpolicyBlock(String speed){
        return "";
    }
} // **  class AllAboutSpeed
