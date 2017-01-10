package v1ch12;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 1/10/17.
 */
public class PairTest3 {
    public static void main(String[] args) {
        Manager ceo = new Manager("Daniel Bond", 800000, 2003, 12, 15);
        Manager cfo = new Manager("Jason Bourne", 60000, 2003, 12, 15);
        Pair<Manager> buddies = new Pair<Manager>(ceo, cfo);
        printBuddies(buddies);

        ceo.setBonus(1000000);
        cfo.setBonus(500000);
        Manager[] managers = { ceo, cfo };

        Pair<Employee> result = new Pair<Employee>();
        minmaxBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName()
                + ", second:" + result.getSecond().getName());

        maxminBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName()
                + ", second:" + result.getSecond().getName());
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        System.out.println(p.getFirst().getName()
                + " and "
                + p.getSecond().getName()
                + " are buddies");
    }

    public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if (a == null || a.length == 0) return;
        Manager min = a[0], max = a[0];
        for (int i = 0; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) min = a[i];
            if (max.getBonus() < a[i].getBonus()) max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
    }

    public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a, result);
        PairAlg.swapHelper(result);
    }
}

class PairAlg {
    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }

    public static void swap(Pair<?> p) {
        swapHelper(p);
    }

    public static <T> void swapHelper(Pair<T> p) {
        T first = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(first);
    }
}

class Employee {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        hireDay = calendar.getTime();
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double byPercent) {
        salary += salary * byPercent / 100;
    }
}

class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
    }

    @Override
    public double getSalary() {
        return super.getSalary() + bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}