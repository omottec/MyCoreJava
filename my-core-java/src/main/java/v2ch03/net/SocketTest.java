package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class SocketTest {
    public static void main(String[] args) {
        String host = "www.baidu.com";
        Socket socket = null;
        try {
//            socket = new Socket(host, 80);
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, 80), 3000);
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNextLine())
                System.out.println(scanner.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(socket);
        }
    }
}
