package MyWork.Tools;

import MyWork.Config.SPEED_TYPE;

import java.util.ArrayList;

public class CiscoSpeedFormat {
    private static final int KB = 1024;

    public static ArrayList<String> getFormattedSpeed(SPEED_TYPE type, String speed) {
        ArrayList<String> result = new ArrayList<>();

        String speedLowCase = speed.toLowerCase();
        double speedSize;
        try {
            speedSize = Integer.valueOf(speedLowCase.split("\\D")[0]);
        } catch (Exception ex) {
            result.add("[!!!] Error. ");
            result.add("\tFailed split speed.");
            return result;
        }

        if (type != null) {
            switch (type) {
                case RATE:
                    if (speedLowCase.contains("m")) {
                        String spBlock = (int) (1024000 * speedSize) + " " +
                                (int) (192000 * speedSize) + " " +
                                (int) (384000 * speedSize) + " ";

                        result.add("rate-limit input " +
                                spBlock +
                                "conform-action transmit exceed-action drop");
                        result.add("rate-limit output " +
                                spBlock +
                                "conform-action transmit exceed-action drop");

                    } else if (speedLowCase.contains("k")) {
                        double inMB = speedSize / KB;
                        String spBlock = (int) (1024000 * inMB) + " " +
                                (int) (192000 * inMB) + " " +
                                (int) (384000 * inMB) + " ";

                        result.add("rate-limit input " +
                                spBlock +
                                "conform-action transmit exceed-action drop");
                        result.add("rate-limit output " +
                                spBlock +
                                "conform-action transmit exceed-action drop");

                    } else {
                        result.add("[!!!] Error. ");
                        result.add("\tNot found speed size.");
                    }
                    break;
                case POLICY:
                    if (speedLowCase.contains("m")) {
                        double speedResult = speedSize * KB;
                        result.add("service-policy input " + (int) speedResult + "k");
                        result.add("service-policy output " + (int) speedResult + "k");

                    } else if (speedLowCase.contains("k")) {
                        result.add("service-policy input " + speedLowCase);
                        result.add("service-policy output " + speedLowCase);

                    } else {
                        result.add("[!!!] Error. ");
                        result.add("\tNot found speed size.");
                    }
                    break;
            } // switch (type)
        } else {
            result.add("[!!!] Error. ");
            result.add("\tSpeed type is null. ");
        }

        System.out.println("result " + result);
        System.out.println("speed " + speed);
        System.out.println("divider = " + speedSize / KB);

        return result;
    } // ** getFormattedSpeed(String type)

} // **  class CiscoSpeedFormat
