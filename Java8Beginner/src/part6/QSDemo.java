package part6;

public class QSDemo {
    public static void main(String[] args) {

        char[] list = {'a', 'c', 'd', 'b', 'f', 'h', 'g', 'e'};
        System.out.print("Не отсортирован: ");
        for(char c : list) {
            System.out.print(c);
        }

        System.out.println();
        QuickSort.qSort(list);

        System.out.print("Отсортирован: ");
        for(char c : list) {
            System.out.print(c);
        }
    }
}


class QuickSort {

    static void qSort(char[] items) {
        qs(items, 0, items.length - 1);
    }

    private static void qs(char[] items, int left, int right) {

        char x, y;
        int i = left;
        int j = right;
        x = items[(left+right)/2];

        do {
            while ((items[i] < x) && (i < right)) i++;
            while ((x < items[j]) && (j > left)) j--;

            if(i <= j) {
                y = items[i];
                items[i] = items[j];
                items[j] = y;
                i++; j--;
            }
        } while (i <= j);
        if (left < j) qs(items, left, j);
        if (right > i) qs(items, i, right);
    }
}