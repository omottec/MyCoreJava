package v1ch12;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;

/**
 * Created by qinbingbing on 1/10/17.
 */
public class PairTest2 {
    public static void main(String[] args) {
        testGenericArray();
    }

    public static void test() {
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

    public static void testGetMiddle() {
//        double middle = ArrayAlg.getMiddle(3.14, 1729, 0);
        Number middle = ArrayAlg.getMiddle(3.14, 1729, 0);
//        String s = ArrayAlg.getMiddle("Hello", 0, null);
        Serializable mid = ArrayAlg.getMiddle("Hello", 0, null);
    }

    public static void testGenericArray() {
        /*Pair<String>[] table = new Pair[10];
        Object[] objArray = table;
        objArray[0] = "Hello";
        objArray[0] = new Pair<Employee>();*/

//        String[] ss = ArrayAlg.minmax1(new String[]{"Daniel Bond", "Jason Bourne", "Ethan Hunt"});
//        String[] ss = ArrayAlg.minmax2(new String[]{"Daniel Bond", "Jason Bourne", "Ethan Hunt"});

        Manager ceo = new Manager("Daniel Bond", 1000000, 1989, 9, 3);
        Manager cfo = new Manager("Jason Bourne", 800000, 1990, 10, 3);
        Manager[] managerBuddies = { ceo, cfo };
        Employee[] employeeBuddies = managerBuddies;
        employeeBuddies[0] = new Employee("Ethan Hunt", 600000, 1991, 11, 3);
    }

    public static void testGenericInherit() {
        Manager ceo = new Manager("Daniel Bond", 1000000, 1989, 9, 3);
        Manager cfo = new Manager("Jason Bourne", 800000, 1990, 10, 3);
        Manager manager = new Manager("Ethan Hunt", 600000, 1991, 11, 3);
        Employee employee = new Employee("Ethan Hunt", 600000, 1991, 11, 3);
        Pair<Manager> managerBuddies = new Pair<Manager>(ceo, cfo);
        Pair rawBuddies = managerBuddies;
        rawBuddies.setFirst(new File(""));

        Pair<? extends Employee> wildcardBuddies = managerBuddies;
//        wildcardBuddies.setFirst(new Employee("Ethan Hunt", 600000, 1991, 11, 3));
//        wildcardBuddies.setFirst(new Manager("Ethan Hunt", 600000, 1991, 11, 3));
        employee = wildcardBuddies.getFirst();
//        manager = wildcardBuddies.getFirst();

        Pair<? super Manager> wildcardPair = new Pair<Manager>();
        wildcardPair.setFirst(manager);
//        wildcardPair.setFirst(employee);

        Pair pair = new Pair();
        pair.setFirst("aaa");

        Pair<?> pair1 = new Pair<Object>();
//        pair1.setFirst(new Object());
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

    public static <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }

    public static <T extends Comparable<? super T>> T[] minmax1(T[] a) {
        Object[] mm = new Object[2];
        return (T[]) mm;
    }

    public static <T extends Comparable<? super T>> T[] minmax2(T[] a) {
        return (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
    }
}
