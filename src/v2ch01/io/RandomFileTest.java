package v2ch01.io;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by qinbingbing on 8/10/16.
 */
public class RandomFileTest {
    public static void main(String[] args) {
        DataEmployee[] staff = new DataEmployee[3];
        staff[0] = new DataEmployee(100000, "James Bond", 1989, 10, 3);
        staff[1] = new DataEmployee(80000, "Jason Bourne", 1990, 11, 3);
        staff[2] = new DataEmployee(60000, "Ethan Hunt ", 1991, 12, 3);

        String fileName = "employee.dat";
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(fileName));
            for (DataEmployee e : staff)
                e.writeData(dos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(dos);
        }

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "r");
            int n = (int) (raf.length() / DataEmployee.RECORD_SIZE);
            staff = new DataEmployee[n];
            for (int i = n - 1; i >= 0; i--) {
                staff[i] = new DataEmployee();
                raf.seek(i * DataEmployee.RECORD_SIZE);
                staff[i].readData(raf);
            }
            for (DataEmployee e : staff)
                System.out.println(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(raf);
        }
    }

}

class DataEmployee extends Employee {
    public static final int NAME_SIZE = 40;
    public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

    public DataEmployee(double salary, String name, int year, int month, int day) {
        super(salary, name, year, month, day);
    }

    public DataEmployee() {
    }

    public void writeData(DataOutput out) throws IOException {
        IoUtils.writeFixedString(getName(), NAME_SIZE, out);
        out.writeDouble(getSalary());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getHireDay());
        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH) + 1);
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void readData(DataInput in) throws IOException {
        setName(IoUtils.readFixedString(NAME_SIZE, in));
        setSalary(in.readDouble());
        int y = in.readInt();
        int m = in.readInt() - 1;
        int d = in.readInt();
        GregorianCalendar calendar = new GregorianCalendar(y, m, d);
        setHireDay(calendar.getTime());
    }
}

