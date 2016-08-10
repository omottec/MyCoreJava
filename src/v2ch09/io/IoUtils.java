package v2ch09.io;

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
}
