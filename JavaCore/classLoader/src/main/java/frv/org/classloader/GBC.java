/*
 * Created by Roman V. Fedorin
 */
package frv.org.classloader;

import java.awt.GridBagConstraints;

/**
 *
 * @author Wolf
 */
public class GBC extends GridBagConstraints {

    public GBC(int xPos, int yPos) {
        this.gridx = xPos;
        this.gridy = yPos;
    }

    public GBC weight(int xWeight, int yWeight) {
        this.weightx = xWeight;
        this.weighty = yWeight;

        return this;
    }

    public GBC gridWH(int xWeight, int yWeight) {
        this.gridwidth = xWeight;
        this.weighty = yWeight;

        return this;
    }

    public GBC anchor(String side) {
        side = side.toUpperCase();
        switch (side) {
            case ("WEST"):
                this.anchor = GridBagConstraints.WEST;
                break;
            case ("EAST"):
                this.anchor = GridBagConstraints.WEST;
                break;
        }

        return this;
    }

}
