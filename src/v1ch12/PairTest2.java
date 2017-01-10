package v1ch12;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 1/10/17.
 */
public class PairTest2 {
    public static void main(String[] args) {
        GregorianCalendar[] birthdays = {
                new GregorianCalendar(1906, Calendar.DECEMBER, 9),
                new GregorianCalendar(1815, Calendar.DECEMBER, 10),
                new GregorianCalendar(1903, Calendar.DECEMBER, 3),
                new GregorianCalendar(1910, Calendar.JUNE, 22)
        };
        Pair<GregorianCalendar> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min=" + mm.getFirst());
        System.out.println("max=" + mm.getSecond());
    }
}

class ArrayAlg {
    public static <T extends Comparable<? super T>> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0], max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<T>(min, max);
    }
}
