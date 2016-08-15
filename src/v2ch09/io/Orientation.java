package v2ch09.io;

import java.io.*;

/**
 * Created by qinbingbing on 8/15/16.
 */
public class Orientation implements Serializable {
    public static final int HORIZONTAL_VALUE = 1;
    public static final int VERTICAL_VALUE = 2;
    public static final Orientation HORIZONTAL = new Orientation(HORIZONTAL_VALUE);
    public static final Orientation VERTICAL = new Orientation(VERTICAL_VALUE);

    private int value;

    private Orientation(int value) {
        this.value = value;
    }

    protected Object readResolve() {
        switch (value) {
            case HORIZONTAL_VALUE:
                return HORIZONTAL;
            case VERTICAL_VALUE:
                return VERTICAL;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (value) {
            case HORIZONTAL_VALUE:
                sb.append("HORIZONTAL");
                break;
            case VERTICAL_VALUE:
                sb.append("VERTICAL");
                break;
        }
        sb.append("@").append(hashCode());
        return sb.toString();
    }

    public static void main(String[] args) {
        String fileName = "Orientation.out";
        ObjectOutputStream oos = null;
        Orientation orientation = HORIZONTAL;
        System.out.println("origin:" + orientation);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(orientation);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(oos);
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            Orientation saved = (Orientation) ois.readObject();
            System.out.println("saved:" + saved);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(ois);
        }
    }
}
