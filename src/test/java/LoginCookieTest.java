import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by linweili on 2018/8/29/0029.
 */
public class LoginCookieTest {

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish
    private static final String domain = "DESede";

    public static void main(String[] main) throws UnsupportedEncodingException {
        String value = "";
        TripleDESEncrypt(value, "~#^&tuandai*%#junte#111!");
        value = URLEncoder.encode(value, "UTF-8");
        String key = "tdw";
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setDomain("http://www.tuandai.com");
        cookie.setMaxAge(-1);

    }

    static String TripleDESEncrypt(String original, String key) {

        try {
            byte[] keybyte = key.substring(0, 24).getBytes();
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.getEncoder().encodeToString(c1.doFinal(original.getBytes("UTF-8")));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

}
