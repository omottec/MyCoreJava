package v2ch09.io;

import java.io.*;

/**
 * Created by qinbingbing on 8/15/16.
 */
public class ObjectStreamTest {
    public static void main(String[] args) {
        Employee hunt = new Employee(60000, "Ethan Hunt", 1990, 9, 24);
        Manager bond = new Manager(100000, "Daniel Bond", 1989, 10, 3);
        bond.setSecretary(hunt);
        Manager bourne = new Manager(80000, "Jason Bourne", 1991, 12, 6);
        bourne.setSecretary(hunt);
        Employee[] staff = { hunt, bond, bourne };

        String fileName = "employee.dat";
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(staff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(oos);
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            Employee[] newStaff = (Employee[]) ois.readObject();
            newStaff[1].raiseSalary(10);
            for (Employee e : newStaff)
                System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(ois);
        }
    }
}

class Manager extends Employee {
    private Employee secretary;

    public Manager(double salary, String name, int year, int month, int day) {
        super(salary, name, year, month, day);
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return super.toString() + "[secretary=" + secretary + "]";
    }
}
