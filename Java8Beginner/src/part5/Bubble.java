package part5;


public class Bubble {
    private int[] toSort;

    Bubble(int[] mass) {
        toSort = mass;
    }

    public void sort() {
        int size = toSort.length;
        for (int i = 0; i < size-1; i++) {
//            System.out.println();
//            System.out.println("loop is number " + i);
            for(int j = size - 1; j > i; j--) {
//                System.out.println(" in toSize[" + j + "]");
                if (toSort[j-1] > toSort[j]) {
                    int temp = toSort[j];
                    toSort[j] = toSort[j-1];
                    toSort[j-1] = temp;
                }
            }
        }
    } // close sort()

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");

        for (int i : toSort) {
            str.append(i + " ");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int[] m = {1, 4, 3, 4, 6};
        Bubble sort = new Bubble(m);

        System.out.println(sort);
        sort.sort();
        System.out.println(sort);

    }
} // close class Bubble
