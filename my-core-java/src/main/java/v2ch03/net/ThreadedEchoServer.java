package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class ThreadedEchoServer {
    private static int count;
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8189);
            Socket s;
            while (true) {
                s = ss.accept();
                System.out.println("Spawning " + count++);
                executorService.submit(new EchoRunnable(s));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class EchoRunnable implements Runnable {
    private Socket socket;

    public EchoRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Hello! Enter BYE to exit.");
            boolean done = false;
            String line;
            while (!done && scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println("Echo: " + line);
                if (line.trim().equals("BYE")) done = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(socket);
        }
    }
}