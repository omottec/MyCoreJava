package v2ch09.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by qinbingbing on 8/10/16.
 */
public class IoUtils {
    public static void close(AutoCloseable... autoCloseables) {
        if (autoCloseables == null || autoCloseables.length == 0) return;
        for (AutoCloseable autoCloseable : autoCloseables)
            if (autoCloseable != null)
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        char ch;
        for (int i = 0; i < size; i++) {
            ch = 0;
            if (i < s.length())
                ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder sb = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        char ch;
        while (more && i < size) {
            ch = in.readChar();
            i++;
            if (ch == 0)
                more = false;
            else
                sb.append(ch);
        }
        in.skipBytes((size - i) * 2);
        return sb.toString();
    }
}
