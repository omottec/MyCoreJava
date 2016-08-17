package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class EchoServer {
    public static void main(String[] args) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8189);
            Socket socket = ss.accept();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello! Enter BYE to exit.");
            boolean done = false;
            while (!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.println("Echo: " + line);
                if ("BYE".equals(line.trim())) done = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(ss);
        }
    }
}
