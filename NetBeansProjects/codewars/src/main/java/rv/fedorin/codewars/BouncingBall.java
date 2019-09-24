/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

/**
 *
 * @author R. V. Fedorin
 */
public class BouncingBall {

    public static int bouncingBall(double h, double bounce, double window) {
        if (h < 0 || bounce < 0 || bounce > 1 || window > h) {
            return -1;
        }
        int times = getBounces(h, bounce, window);

        return times-2;
    }
    
    private static int getBounces(double h, double bounce, double window) {
        int i = 0;
        if(window >= h) {
            return 1;
        } else {
            i += getBounces((h * bounce), bounce, window) + 2;
        }
        return i;
    }

    public static void main(String[] args) {
        // h = 3, bounce = 0.66, window = 1.5, result is 3
        System.out.println("Count " + BouncingBall.bouncingBall(3, 0.66, 1.5));
        System.out.println("Count " + BouncingBall.bouncingBall(30, 0.66, 1.5));
        
    }

}
