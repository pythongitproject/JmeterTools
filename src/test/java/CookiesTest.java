import org.apache.commons.lang3.time.DateFormatUtils;
import td.functions.Cryptography;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

public class CookiesTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String key = "~#^&tuandai*%#junte#111!";
        String userid = "5a322e1d-aeeb-43da-ba3e-5057e1d6bd96";
        String timeValue = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        String userValue = userid + "|" + timeValue + "|" + "-Chrome";
        String a1 = Cryptography.tripleDESEncrypt(userValue,key);
        String fn = URLEncoder.encode(a1, "UTF-8");
        System.out.println(fn);
        String jn = URLDecoder.decode("krulawYX0dZrEwUgfb%2FQOzSzwMpGalPM4iqHHMfbrjWmGeH1AmqRVA%3D%3D", "UTF-8");
        String jm = Cryptography.tripleDESDecrypt(jn,key);

        System.out.println(jm);
    }
}
