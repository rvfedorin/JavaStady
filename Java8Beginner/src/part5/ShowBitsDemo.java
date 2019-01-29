package part5;

public class ShowBitsDemo {
    public static void main(String[] args) {
        ShowBits bit = new ShowBits(32);
        bit.show(12385);
    } // close main()
} // close class ShowBitsDemo

class ShowBits {
    private int numbits;

    ShowBits(int num) {
        numbits = num;
    }

    public void show(long val) {
        long mask = 1;

        mask <<= numbits - 1;

        int spacer = 0;
        for(; mask != 0; mask >>>= 1) {
            if((val & mask) != 0) System.out.print("1");
            else System.out.print("0");
            spacer ++;
            if ((spacer % 8) == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    } // close show()
}// close class ShowBits
