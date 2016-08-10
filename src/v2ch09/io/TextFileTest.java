package v2ch09.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by qinbingbing on 8/10/16.
 */
public class TextFileTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee(100000, "James Bond", 1987, 12, 15);
        staff[1] = new Employee(80000, "Jason Bourne", 1989, 11, 13);
        staff[2] = new Employee(50000, "Ethan Hunt", 1990, 10, 3);
        String fileName = "employee.dat";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
            writeData(staff, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("IoUtils.close(writer)");
            IoUtils.close(writer);
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(fileName));
            staff = readData(scanner);
            for (Employee e : staff)
                System.out.println(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("IoUtils.close(scanner)");
            IoUtils.close(scanner);
        }
    }

    private static void writeData(Employee[] employees, PrintWriter writer) {
        writer.println(employees.length);
        for(Employee e : employees)
            e.writeData(writer);
    }

    private static Employee[] readData(Scanner scanner) {
        int n = scanner.nextInt();
        scanner.nextLine();
        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = new Employee();
            employees[i].readData(scanner);
        }
        return employees;
    }
}

class Employee {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee() {

    }

    public Employee(double salary, String name, int year, int month, int day) {
        this.salary = salary;
        this.name = name;
        hireDay = new GregorianCalendar(year, month - 1, day).getTime();
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
        return getClass().getName() + "[name=" + name
                + ", salary=" + salary
                + ", hireDay=" + hireDay
                + "]";
    }

    public void writeData(PrintWriter writer) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(hireDay);
        writer.println(name + "|"
                + salary + "|"
                + calendar.get(Calendar.YEAR) + "|"
                + (calendar.get(Calendar.MONTH) + 1) + "|"
                + calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void readData(Scanner scanner) {
        String line = scanner.nextLine();
        String[] tokens = line.split("\\|");
        name = tokens[0];
        salary = Double.parseDouble(tokens[1]);
        int y = Integer.parseInt(tokens[2]);
        int m = Integer.parseInt(tokens[3]) - 1;
        int d = Integer.parseInt(tokens[4]);
        GregorianCalendar calendar = new GregorianCalendar(y, m, d);
        hireDay = calendar.getTime();
    }
}