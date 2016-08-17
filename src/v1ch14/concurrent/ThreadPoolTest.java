package v1ch14.concurrent;

import v2ch01.io.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter base directory(e.g. /usr/local/jdk5.0/src): ");
        String dir = scanner.nextLine();
        System.out.println("Enter keyword(e.g. volatile): ");
        String keyword = scanner.nextLine();
        final ExecutorService pool = Executors.newCachedThreadPool();
        CounterCall counterCall = new CounterCall(keyword, new File(dir), pool);
        Future<Integer> result = pool.submit(counterCall);
        /*pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
//                    pool.shutdown();
                    pool.shutdownNow();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/
        try {
            System.out.println(result.get() + " matching files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
//        pool.shutdownNow();
        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largestPoolSize: " + largestPoolSize);
    }
}

class CounterCall implements Callable<Integer> {
    private File directory;
    private String keyword;
    private int count;
    private ExecutorService executor;

    public CounterCall(String keyword, File directory, ExecutorService executor) {
        this.keyword = keyword;
        this.directory = directory;
        this.executor = executor;
    }



    @Override
    public Integer call() {
        System.out.println(Thread.currentThread());
        count = 0;
        File[] files = directory.listFiles();
        ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
        for (File file : files)
            if (file.isDirectory()) {
                CounterCall counter = new CounterCall(keyword, file, executor);
                Future<Integer> task = executor.submit(counter);
                results.add(task);
            } else {
                if (search(file)) count++;
            }

        for (Future<Integer> result : results)
            try {
                count += result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        return count;
    }

    public boolean search(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
            boolean found = false;
            while (!found && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(keyword)) found = true;
            }
            TimeUnit.MILLISECONDS.sleep(100);
            return found;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.close(scanner);
        }
    }
}
