package v1ch06;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 8/15/16.
 */
public class ArrayCloneTest {
    public static void main(String[] args) {
        int[] luckyNumber = { 1, 3, 5, 7, 9, 11 };
        int[] clone = luckyNumber.clone();
        System.out.println("luckyNumber:" + Arrays.toString(luckyNumber));
        System.out.println("clone:" + Arrays.toString(clone));
        clone[5] = 12;
        System.out.println("luckyNumber:" + Arrays.toString(luckyNumber));
        System.out.println("clone:" + Arrays.toString(clone));

        System.out.println("-------------------------------");
        String[] originNames = { "James Bond", "Jason Bourne", "Ethan Hunt" };
        String[] cloneNames = originNames.clone();
        System.out.println("originNames:" + Arrays.toString(originNames));
        System.out.println("cloneNames:" + Arrays.toString(cloneNames));
        cloneNames[0] = "Daniel Bond";
        System.out.println("originNames:" + Arrays.toString(originNames));
        System.out.println("cloneNames:" + Arrays.toString(cloneNames));

        System.out.println("------------------------------------");
        Date[] originDates = new Date[2];
        GregorianCalendar calendar = new GregorianCalendar(2016, 7, 14);
        originDates[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        originDates[1] = calendar.getTime();
        Date[] cloneDates = originDates.clone();
        System.out.println("originDates:" + Arrays.toString(originDates));
        System.out.println("cloneDates:" + Arrays.toString(cloneDates));
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        cloneDates[1] = calendar.getTime();
        System.out.println("originDates:" + Arrays.toString(originDates));
        System.out.println("cloneDates:" + Arrays.toString(cloneDates));
    }
}
