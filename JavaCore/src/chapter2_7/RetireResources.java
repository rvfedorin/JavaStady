package chapter2_7;

import java.awt.*;


public class RetireResources extends java.util.ListResourceBundle
{
    private static final Object[][] contents = {
            // BEGIN LOCALIZE
            { "colorPre", Color.blue }, { "colorGain", Color.white }, {
            "colorLoss", Color.red }
            // END LOCALIZE
    };

    public Object[][] getContents()
    {
        return contents;
    }
}