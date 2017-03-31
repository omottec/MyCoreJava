package v2ch03.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by qinbingbing on 8/16/16.
 */
public class InetAddressTest {
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                String host = args[0];
                InetAddress[] addresses = InetAddress.getAllByName(host);
                for (InetAddress a : addresses)
                    System.out.println(a);
            } else {
                InetAddress localHost = InetAddress.getLocalHost();
                System.out.println(localHost);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
