package v2ch09.io;

import java.io.*;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class SerialCloneTest {
    public static void main(String[] args) {
        Staff staff = new Staff("Daniel Bond", 100000, 1989, 10, 3);
        Staff staff1 = (Staff) staff.clone();
        System.out.println("staff:" + staff);
        System.out.println("staff1:" + staff1);
        staff1.raiseSalary(10);
        System.out.println("-----------------------------");
        System.out.println("staff:" + staff);
        System.out.println("staff1:" + staff1);
    }
}

class SerialCloneable implements Serializable, Cloneable {
    @Override
    public Object clone() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bout);
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(out);
        }

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bin);
            return in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(in);
        }
        return null;
    }
}

class Staff extends SerialCloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Staff(String name, double salary, int y, int m, int d) {
        this.name = name;
        this.salary = salary;
        GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
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
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return getClass().getName()
                + "[name=" + name
                + ", salary=" + salary
                + ", hireDay=" + hireDay
                + ", hashCode=" + hashCode()
                + "]";
    }
}


