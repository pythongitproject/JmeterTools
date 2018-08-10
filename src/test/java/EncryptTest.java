import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

public class EncryptTest {
    EncryptUtil1 encryptUti = new EncryptUtil1();
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>(3);
        EncryptTest test = new EncryptTest();
        map.put("appId", "SEOLCef1e7f016");
        long requestTime = new Date().getTime();

        map.put("requestTime", requestTime);
        System.out.println("请输入电话号码");
        Scanner in = new Scanner(System.in);
        String telNo11 = in.next();
        System.out.println("电话号码：" + telNo11);
        if(telNo11 == null || telNo11.length() != 11){
            System.out.println("电话号码长度错误");
            return;
        }
        String telNo = test.encryptUti.encrypt(telNo11, "thirdplatWebToFeifei20180727MSJDHDFHIERUI@U#$*%*#$@!@#(*(&*");

        map.put("telNo", telNo);
        String str = joinParam(map, "&", Boolean.TRUE);
//        System.out.println(str);
        map.put("telNo", telNo11);
        String str1 = joinParam(map, "&", Boolean.TRUE);
//        System.out.println("requestTime:" +requestTime);
//        System.out.println(str1);
        String sign = test.encryptUti.md5(str1);
        map.put("sign", sign);
        map.put("telNo", telNo);
        System.out.println("{");
        System.out.println("appId:\"" +map.get("appId") +"\",");
        System.out.println("requestTime:\"" +map.get("requestTime") +"\",");
        System.out.println("telNo:\"" +map.get("telNo") +"\",");
        System.out.println("sign:\"" +map.get("sign") +"\"");
        System.out.println("}");

//        System.out.println("telNo:  "+telNo);
//        System.out.println("sign:  "+sign);
//        System.out.println(str + "&sign=" +sign);
    }

    public static String joinParam(Map<String, Object> paramMap, String suffix, boolean isSort) {
        Set<String> strings = paramMap.keySet();
        Object[] objects = strings.toArray();
        if (isSort) {
            Arrays.sort(objects);
        }
        StringBuffer paramString = new StringBuffer();
//        Arrays.stream(objects).forEach(key -> paramString.append(key).append("=").append(paramMap.get(key)).append(suffix));
        return paramString.substring(0,paramString.length() -1);
    }

    class EncryptUtil1 {
        private static final String KEY_ALGORITHM = "AES";
        private static final String KEY_ALGORITHM_DESEDE = "DESede";

        public EncryptUtil1() {
        }

        public String encrypt(String content, String encryptKey) {
            if (isBlank(encryptKey) == false) {
                try {
                    KeyGenerator generator = KeyGenerator.getInstance("AES");
                    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                    secureRandom.setSeed(encryptKey.getBytes());
                    generator.init(128, secureRandom);
                    SecretKey secretKey = generator.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    cipher.init(1, key);
                    byte[] result = cipher.doFinal(content.getBytes());
                    return Base64.encodeBase64String(result);
                } catch (Exception var9) {
                }
            }

            return null;
        }

        public String decrypt(String content, String encryptKey) {
            if (isBlank(encryptKey) == false) {
                try {
                    KeyGenerator kgen = KeyGenerator.getInstance("AES");
                    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                    random.setSeed(encryptKey.getBytes());
                    kgen.init(128, random);
                    SecretKey secretKey = kgen.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    cipher.init(2, keySpec);
                    byte[] result = cipher.doFinal(Base64.decodeBase64(content));
                    return new String(result);
                } catch (Exception var9) {
                }
            }

            return null;
        }

        private SecretKeySpec getKey(String password) throws UnsupportedEncodingException {
            int keyLength = 256;
            byte[] keyBytes = new byte[keyLength / 8];
            Arrays.fill(keyBytes, (byte) 0);
            byte[] passwordBytes = password.getBytes("UTF-8");
            int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
            System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            return key;
        }

        public String md5(String text) {
            if (isBlank(text)) {
                return "";
            } else {
                try {
                    StringBuilder sb = new StringBuilder();
                    MessageDigest md = MessageDigest.getInstance("MD5");
//                    md.update(text.getBytes(StandardCharsets.UTF_8));
                    byte[] var3 = md.digest();
                    int var4 = var3.length;

                    for (int var5 = 0; var5 < var4; ++var5) {
                        byte b = var3[var5];
                        int n = b;
                        if (b < 0) {
                            n = b + 256;
                        }

                        if (n < 16) {
                            sb.append("0");
                        }

                        sb.append(Integer.toHexString(n));
                    }

                    return sb.toString();
                } catch (Exception var8) {
                    return null;
                }
            }
        }

        private boolean isBlank(String  aa){
            return aa == null || "".equals(aa);
        }

        public byte[] hex(String key) {
            byte[] bkeys = (new String(key)).getBytes();
            byte[] enk = new byte[24];

            for (int i = 0; i < 24; ++i) {
                enk[i] = bkeys[i];
            }

            return enk;
        }

        public String encrypt3Des(String key, String srcStr) {
            byte[] keybyte = hex(key);
            byte[] src = srcStr.getBytes();

            try {
                SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
                Cipher c1 = Cipher.getInstance("DESede");
                c1.init(1, deskey);
                return Base64.encodeBase64String(c1.doFinal(src));
            } catch (Exception var6) {
                return null;
            }
        }

        public String decrypt3Des(String key, String desStr) {
            Base64 base64 = new Base64();
            byte[] keybyte = hex(key);
            byte[] src = base64.decode(desStr);

            try {
                SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
                Cipher c1 = Cipher.getInstance("DESede");
                c1.init(2, deskey);
                return new String(c1.doFinal(src));
            } catch (Exception var7) {
                return null;
            }
        }
    }
}
