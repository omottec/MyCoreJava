package v2ch01.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        String dir = "." + File.separator + "src"
                + File.separator + "v2ch09"
                + File.separator + "io";
        File file = new File(dir, "FileTest.java");
        System.out.println("file.exists():" + file.exists());
        System.out.println("file.getPath():" + file.getPath());
        System.out.println("file.getAbsolutePath():" + file.getAbsolutePath());
        System.out.println("file.getCanonicalPath():" + file.getCanonicalPath());
        File[] roots = file.listRoots();
        System.out.println("roots:" + Arrays.toString(roots));
        System.out.println("file.length():" + file.length());
        System.out.println("file.getTotalSpace():" + file.getTotalSpace());
        System.out.println("file.getUsableSpace():" + file.getUsableSpace());
        System.out.println("file.getFreeSpace():" + file.getFreeSpace());
        URI uri = file.toURI();
        System.out.println("uri:" + uri);
        File tmpFile = File.createTempFile("qbb", ".java");
        System.out.println("tmpFile.getCanonicalPath():" + tmpFile.getCanonicalPath());
        File tmpFile1 = File.createTempFile(".pic", "dat", new File("."));
        System.out.println("tmpFile1.getCanonicalPath():" + tmpFile1.getCanonicalPath());
        System.out.println("tmpFile1.isHidden():" + tmpFile1.isHidden());;
    }
}
