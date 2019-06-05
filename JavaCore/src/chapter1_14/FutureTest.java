package chapter1_14;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {
    public static void main(String[] args) {
        String dir = "C:\\Users\\Wolf\\Test";
        String word = "work";

        MatchCounter counter = new MatchCounter(new File(dir), word);
        FutureTask<Integer> task = new FutureTask<>(counter);
        Thread t = new Thread(task);
        t.start();

        try {
            System.out.println(task.get() + " matching files.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
} // ** class FutureTest

class MatchCounter implements Callable<Integer> {
    private File file;
    private String word;

    public MatchCounter(File file, String word) {
        this.file = file;
        this.word = word;
    }

    @Override
    public Integer call() {
        int count = 0;
        try {
            File[] files = file.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for(File file: files) {
                if(file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, word);
                    FutureTask<Integer> task = new FutureTask<>(counter);
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if(search(file)) count++;
                }// ** if(file.isDirectory())
            } // ** for files

            for(Future<Integer> result: results) {
                count += result.get();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    } // ** call()

    public boolean search(File file) {
        try {
            try (Scanner in = new Scanner(file)) {
                boolean found = false;
                while (!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    if(line.contains(word)) {
                        found = true;
                        System.out.println(file);
                    }
                } // ** while.
                return found;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    } // ** search(File file)
} // ** class MatchCounter
