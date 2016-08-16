package v2ch09.io;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class RegexTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter pattern:");
        String regex = scanner.nextLine();
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            System.out.println("Enter string to match:");
            String input = scanner.nextLine();
            int length = input.length();
            if (input == null || length == 0) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int groupCount = matcher.groupCount();
                if (groupCount > 0) {
                    System.out.println("groupCount:" + groupCount);
                    for (int i = 0; i < length; i++) {
                        for (int j = 1; j <= groupCount; j++)
                            if (i == matcher.start(j))
                                System.out.print("(");
                        System.out.print(input.charAt(i));
                        for (int j = 1; j <= groupCount; j++)
                            if (i + 1 == matcher.end(j))
                                System.out.print(")");
                    }
                    System.out.println();
                    String replace = matcher.replaceFirst("$2\\$");
                    System.out.println(replace);
                }
            } else {
                System.out.println("No Match");
            }
        }
    }
}
