package v2ch01.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;

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

    public static long checksumInputStream(InputStream is) {
        try {
            CRC32 crc32 = new CRC32();
            int b;
            while ((b = is.read()) != -1)
                crc32.update(b);
            return crc32.getValue();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long checksumFile(String fileName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            return checksumInputStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(fis);
        }
    }

    public static long bufferedChecksumFile(String fileName) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fileName));
            return checksumInputStream(bis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(bis);
        }
    }

    public static long checksumRandomAccessFile(String fileName) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fileName, "r");
            long length = file.length();
            CRC32 crc32 = new CRC32();
            for (int i = 0; i < length; i++) {
                file.seek(i);
                crc32.update(file.readByte());
            }
            return crc32.getValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(file);
        }
    }

    public static long checksumMappedFile(String fileName) {
        FileChannel channel = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            channel = fis.getChannel();
            CRC32 crc32 = new CRC32();
            long size = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            for (int i = 0; i < size; i++)
                crc32.update(buffer.get(i));
            return crc32.getValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(channel);
        }
    }
}
