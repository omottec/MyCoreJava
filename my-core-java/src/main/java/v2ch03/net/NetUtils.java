package v2ch03.net;

import v2ch01.io.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by qinbingbing on 8/17/16.
 */
public final class NetUtils {
    private NetUtils() {}

    public static String doPost(String urlStr, Map<String, String> nameValuePairs) {
//        PrintWriter writer = null;
//        Scanner scanner = null;
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        connection.setDoOutput(true);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(connection.getOutputStream());
            boolean first = false;
            if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
                for (Map.Entry<String, String> pair : nameValuePairs.entrySet()) {
                    if (first)
                        first = false;
                    else
                        writer.print('&');
                    writer.print(pair.getKey());
                    writer.print('=');
                    writer.print(URLEncoder.encode(pair.getValue(), "UTF-8"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(writer);
        }

        Scanner scanner = null;
        StringBuilder resp = new StringBuilder();
        try {
            scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine())
                resp.append(scanner.nextLine()).append('\n');
            return resp.toString();
        } catch (IOException e) {
            e.printStackTrace();
            if (!(connection instanceof HttpURLConnection)) {
                e.printStackTrace();
                return null;
            }
            InputStream err = ((HttpURLConnection) connection).getErrorStream();
            if (err == null) {
                e.printStackTrace();
                return null;
            }
            scanner = new Scanner(err);
            while (scanner.hasNextLine())
                resp.append(scanner.nextLine()).append('\n');
            return resp.toString();
        } finally {
            IoUtils.close(scanner);
        }

    };
}
