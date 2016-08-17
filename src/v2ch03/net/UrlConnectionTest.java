package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class UrlConnectionTest {
    public static void main(String[] args) {
        String urlStr;
        if (args.length > 0)
            urlStr = args[0];
        else
            urlStr = "http://java.sun.com";
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            if (args.length > 2) {
                String username = args[1];
                String pwd = args[2];
                String userInfo = username + ":" + pwd;
                String encode = IoUtils.base64Encode(userInfo);
                connection.setRequestProperty("Authorization", "Basic " + encode);
            }
            connection.connect();
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                String key = header.getKey();
                List<String> values = header.getValue();
                for (String value : values)
                    System.out.println(key + ": " + value);
            }

            System.out.println("------------------------");
            System.out.println("connection.getContentType(): " + connection.getContentType());
            System.out.println("connection.getContentLength(): " + connection.getContentLength());
            System.out.println("connection.getContentEncoding(): " + connection.getContentEncoding());
            System.out.println("connection.getDate(): " +  new Date(connection.getDate()));
            System.out.println("connection.getExpiration(): " + new Date(connection.getExpiration()));
            System.out.println("connection.getLastModified(): " + new Date(connection.getLastModified()));
            System.out.println("-------------------------");

            Scanner scanner = new Scanner(connection.getInputStream());
            for (int i = 1; scanner.hasNextLine() && i <= 10; i++)
                System.out.println(scanner.nextLine());
            if (scanner.hasNextLine())
                System.out.println("...");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


