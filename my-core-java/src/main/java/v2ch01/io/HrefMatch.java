package v2ch01.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class HrefMatch {
    public static void main(String[] args) {
        String urlStr;
        if (args.length > 0)
            urlStr = args[0];
        else
            urlStr = "https://github.com";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            String regex = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sb);
            while (matcher.find())
                System.out.println(sb.subSequence(matcher.start(), matcher.end()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(br);
        }
    }
}
