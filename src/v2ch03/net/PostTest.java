package v2ch03.net;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class PostTest {
    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        String response = NetUtils.doPost(url, null);
        System.out.println(response);
    }
}
