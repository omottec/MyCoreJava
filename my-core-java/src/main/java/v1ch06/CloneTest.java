package v1ch06;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 8/15/16.
 */
public class CloneTest {
    public static void main(String[] args) {
        try {
            Employee original = new Employee("Daniel Bond", 100000);
            original.setHireDay(2009, 10, 3);
            Employee cloned = original.clone();
            System.out.println("original:" + original);
            System.out.println("cloned:" + cloned);

            cloned.raiseSalary(10);
            cloned.setHireDay(2013, 6, 7);
            System.out.println("-----------------------------");
            System.out.println("origin:" + original);
            System.out.println("cloned:" + cloned);

            cloned.changeName("James Bond");
            System.out.println("-----------------------------");
            System.out.println("origin:" + original);
            System.out.println("cloned:" + cloned);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

class Employee implements Cloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        hireDay = new Date();
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }

    public void setHireDay(int year, int month, int day) {
        Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();
        hireDay.setTime(newHireDay.getTime());
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee[name=" + name
                + ", salary=" + salary
                + ", hireDay=" + hireDay + "]";
    }
}
