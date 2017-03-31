package v2ch01.io;

import java.io.File;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class NioTest {
    public static void main(String[] args) {
        String fileName = null;
        if (args.length >= 1)
            fileName = args[0];
        else
            fileName = "" + File.separator + "src"
                    + File.separator + "v2ch09"
                    + File.separator + "io"
                    + File.separator + "NioTest.java";
        String format = "%s\t%dms\n";
        System.out.println("InputStream");
        long start = System.currentTimeMillis();
        long crcValue = IoUtils.checksumFile(fileName);
        long end = System.currentTimeMillis();
        System.out.printf(format, Long.toHexString(crcValue), end - start);

        System.out.println("BufferedInputStream");
        start = System.currentTimeMillis();
        crcValue = IoUtils.bufferedChecksumFile(fileName);
        end = System.currentTimeMillis();
        System.out.printf(format, Long.toHexString(crcValue), end - start);

        System.out.println("RandomAccessFile");
        start = System.currentTimeMillis();
        crcValue = IoUtils.checksumRandomAccessFile(fileName);
        end = System.currentTimeMillis();
        System.out.printf(format, Long.toHexString(crcValue), end - start);

        System.out.println("MappedFile");
        start = System.currentTimeMillis();
        crcValue = IoUtils.checksumMappedFile(fileName);
        end = System.currentTimeMillis();
        System.out.printf(format, Long.toHexString(crcValue), end - start);

        /*InputStream
        5c61f64d       	48206ms
                BufferedInputStream
        5c61f64d       	410ms
                RandomAccessFile
        5c61f64d       	73859ms
                MappedFile
        5c61f64d       	289ms*/
    }
}
