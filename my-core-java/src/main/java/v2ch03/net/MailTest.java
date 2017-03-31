package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class MailTest {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("mail.didichuxing.com", 25);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            String hostName = InetAddress.getLocalHost().getHostName();
            StringBuilder sb = new StringBuilder();
            sb.append("HELO " + hostName).append("\r\n")
                    .append("MAIL FROM: <qinbingbing@didichuxing.com>").append("\r\n")
                    .append("ECPT TO: <omottec007@163.com>").append("\r\n")
                    .append("DATA").append("\r\n")
                    .append("");
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(socket);
        }
    }
}
