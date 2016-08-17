package v2ch01.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class FindDirectories {
    public static void main(String[] args) {
        if (args.length == 0)
            args = new String[] { ".." };
        File file = new File(args[0]);
        String[] fileNames = file.list();
        if (fileNames == null || fileNames.length == 0) {
            System.out.println("fileNames == null || fileNames.length == 0");
            return;
        }
        File f;
        try {
            for (String str : fileNames) {
                f = new File(file.getPath(), str);
                if (f.isDirectory()) {
                    System.out.println(f.getCanonicalPath());
                    main(new String[] {f.getPath()});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
