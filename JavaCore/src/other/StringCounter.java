package other;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class StringCounter implements Callable<Integer> {
    private File file;
    private ExecutorService pool;

    public StringCounter(File path, ExecutorService pool) {
        this.pool = pool;
        this.file = path;
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Wolf\\Documents\\GitHub\\JavaStudy\\JavaCore\\src\\MyWork";

        ExecutorService pool = Executors.newCachedThreadPool();
        StringCounter stringCounter = new StringCounter(new File(path), pool);
        FutureTask<Integer> task = new FutureTask<>(stringCounter);

        pool.submit(task);

        try {
            System.out.println("Всего строк кода: " + task.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pool.shutdown();
    } // ** main()

    @Override
    public Integer call(){
        int count = 0;
        try {
            File[] files = file.listFiles();
            List<Future<Integer>> results = new ArrayList<>();
            for(File file: files) {
                if(file.isDirectory()) {
                    StringCounter counter = new StringCounter(file, pool);
                    FutureTask<Integer> result = new FutureTask<>(counter);
                    results.add(result);
                    pool.submit(result);
                } else {
                    int temp = stringIn(file);
                    count += temp;
                    System.out.println(file + " строк: " + temp);
                }
            }

            for (Future<Integer> result: results) {
                count += result.get();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return count;
    }

    private int stringIn(File file) {
        int result = 0;
        try (Scanner in = new Scanner(file)){
            while (in.hasNextLine()) {
                String str = in.nextLine();
                if(str.trim().length() > 0)
                    result ++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    } // ** stringIn()


} // ** class StringCounter
