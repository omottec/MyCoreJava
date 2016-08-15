package v1ch06;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 8/15/16.
 */
public class CopyTest {
    public static void main(String[] args) {
        String origin = "James Bond";
        String copy = origin;
        System.out.println("origin:" + origin + origin.hashCode());
        System.out.println("copy:" + copy + copy.hashCode());

        copy = "Daniel Bond";
        System.out.println("origin:" + origin + origin.hashCode());
        System.out.println("copy:" + copy + copy.hashCode());

        GregorianCalendar calendar = new GregorianCalendar(2009, 9, 1);
        Date originalDate = calendar.getTime();
        Date copyDate = originalDate;
        System.out.println("originalDate:" + originalDate);
        System.out.println("copyDate:" + copyDate);
        copyDate.setMonth(10);
        System.out.println("originalDate:" + originalDate);
        System.out.println("copyDate:" + copyDate);
    }
}
