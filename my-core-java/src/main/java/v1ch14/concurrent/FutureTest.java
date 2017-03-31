package v1ch14.concurrent;

import v2ch01.io.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class FutureTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter base directory(e.g. /usr/local/jdk5.0/src): ");
        String dir = scanner.nextLine();
        System.out.println("Enter keyword(e.g. volatile): ");
        String keyword = scanner.nextLine();
        MatchCounter counter = new MatchCounter(keyword, new File(dir));
        FutureTask<Integer> task = new FutureTask<Integer>(counter);
        new Thread(task).start();
        try {
            System.out.println(task.get() + " matching files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;
    private int count;

    public MatchCounter(String keyword, File directory) {
        this.keyword = keyword;
        this.directory = directory;
    }



    @Override
    public Integer call() {
        System.out.println(Thread.currentThread());
        count = 0;
        File[] files = directory.listFiles();
        ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
        for (File file : files)
            if (file.isDirectory()) {
                MatchCounter counter = new MatchCounter(keyword, file);
                FutureTask<Integer> task = new FutureTask<Integer>(counter);
                results.add(task);
                new Thread(task).start();
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
            return found;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.close(scanner);
        }
    }
}
