package td.functions;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EncryptUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String KEY_ALGORITHM_DESEDE = "DESede";

    public static String encrypt(String content, String aesPassword) {
        if (StringUtils.isNotBlank(aesPassword)) {
            try {
                KeyGenerator generator = KeyGenerator.getInstance("AES");
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(aesPassword.getBytes());
                generator.init(128, secureRandom);
                SecretKey secretKey = generator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(1, key);
                byte[] result = cipher.doFinal(content.getBytes());
                return Base64.encodeBase64String(result);
            } catch (NoSuchPaddingException var9) {
                var9.printStackTrace();
                return "";
            } catch (NoSuchAlgorithmException var10) {
                var10.printStackTrace();
                return "";
            } catch (InvalidKeyException var11) {
                var11.printStackTrace();
                return "";
            } catch (IllegalBlockSizeException var12) {
                var12.printStackTrace();
                return "";
            } catch (BadPaddingException var13) {
                var13.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static List<String> encrypt(List<String> clearTextList, String aesPassword) {
        if (clearTextList != null && clearTextList.size() > 0) {
            if (StringUtils.isBlank(aesPassword)) {
                return null;
            } else {
                boolean isEncrypt = false;
                List<String> resultList = Lists.newArrayList();
                Iterator var4 = clearTextList.iterator();

                while(var4.hasNext()) {
                    String clearText = (String)var4.next();
                    if("" != clearText){
                        if (!isEncrypt) {
                            isEncrypt = true;
                        }
                        resultList.add(encrypt(clearText, aesPassword));
                    } else {
                        resultList.add("");
                    }
                }
                if (!isEncrypt) {
                    return null;
                }
                return resultList;
            }
        } else {
            return null;
        }
    }

    public static String decrypt(String content, String aesPassword) {
        if (StringUtils.isNotBlank(aesPassword)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(aesPassword.getBytes());
                kgen.init(128, random);
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, keySpec);
                byte[] result = cipher.doFinal(Base64.decodeBase64(content));
                return new String(result);
            } catch (NoSuchAlgorithmException var9) {
                var9.printStackTrace();
                return "";
            } catch (NoSuchPaddingException var10) {
                var10.printStackTrace();
                return "";
            } catch (InvalidKeyException var11) {
                var11.printStackTrace();
                return "";
            } catch (IllegalBlockSizeException var12) {
                var12.printStackTrace();
                return "";
            } catch (BadPaddingException var13) {
                var13.printStackTrace();
                return "";
            }
        }

        return "";
    }

    public static List<String> decrypt(List<String> cipherTextList, String aesPassword) {
        if (cipherTextList != null && cipherTextList.size() == 0) {
            return null;
        } else if (StringUtils.isEmpty(aesPassword)) {
            return null;
        }
        else {
            boolean isDecrypt = false;
            List<String> resultList = Lists.newArrayList();
            Iterator var4 = cipherTextList.iterator();

            while(var4.hasNext()) {
                String cipherText = (String)var4.next();
                if (!StringUtils.isEmpty(cipherText)) {
                    if (!isDecrypt) {
                        isDecrypt = true;
                    }
                    resultList.add(new String(decrypt(cipherText, aesPassword)));
                } else {
                    resultList.add("");
                }
            }
            if (!isDecrypt) {
                return null;
            } else {
                return resultList;
            }
        }
    }

    public static String encryptPKCS7Padding(String content, String aesPassword, String saltString) {
        if (StringUtils.isNotBlank(aesPassword)) {
            try {
                Security.addProvider(new BouncyCastleProvider());
                SecretKeySpec skeySpec = getKey(aesPassword);
                byte[] clearText = content.getBytes("UTF8");
                byte[] iv = saltString.getBytes();
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(1, skeySpec, ivParameterSpec);
                return Base64.encodeBase64String(cipher.doFinal(clearText));
            } catch (InvalidKeyException var8) {
                var8.printStackTrace();
                return "";
            } catch (UnsupportedEncodingException var9) {
                var9.printStackTrace();
                return "";
            } catch (NoSuchAlgorithmException var10) {
                var10.printStackTrace();
                return "";
            } catch (BadPaddingException var11) {
                var11.printStackTrace();
                return "";
            } catch (NoSuchPaddingException var12) {
                var12.printStackTrace();
                return "";
            } catch (IllegalBlockSizeException var13) {
                var13.printStackTrace();
                return "";
            } catch (InvalidAlgorithmParameterException var14) {
                var14.printStackTrace();
                return "";
            }
        }

        return "";
    }

    public static String decryptPKCS7Padding(String content, String aesPassword, String saltString) {
        if (StringUtils.isNotBlank(aesPassword)) {
            try {
                Security.addProvider(new BouncyCastleProvider());
                SecretKeySpec skeySpec = getKey(aesPassword);
                byte[] iv = saltString.getBytes();
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(2, skeySpec, ivParameterSpec);
                return new String(cipher.doFinal(Base64.decodeBase64(content)));
            } catch (InvalidKeyException var7) {
                var7.printStackTrace();
                return "";
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
                return "";
            } catch (NoSuchAlgorithmException var9) {
                var9.printStackTrace();
                return "";
            } catch (BadPaddingException var10) {
                var10.printStackTrace();
                return "";
            } catch (NoSuchPaddingException var11) {
                var11.printStackTrace();
                return "";
            } catch (IllegalBlockSizeException var12) {
                var12.printStackTrace();
                return "";
            } catch (InvalidAlgorithmParameterException var13) {
                var13.printStackTrace();
                return "";
            }
        }

        return "";
    }

    private static SecretKeySpec getKey(String password) throws UnsupportedEncodingException {
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        Arrays.fill(keyBytes, (byte)0);
        byte[] passwordBytes = password.getBytes("UTF-8");
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    public static String md5(String text) {
        if (text != "" && !StringUtils.isEmpty(text.trim())) {
            try {
                StringBuilder sb = new StringBuilder();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(text.getBytes(StandardCharsets.UTF_8));
                byte[] var3 = md.digest();
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
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
                return "";
            }
        } else {
            return "";
        }
    }

    public static byte[] hex(String key) {
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = (new String(f)).getBytes();
        byte[] enk = new byte[24];

        for(int i = 0; i < 24; ++i) {
            enk[i] = bkeys[i];
        }

        return enk;
    }

    public static String encrypt3Des(String key, String srcStr) {
        byte[] keybyte = hex(key);
        byte[] src = srcStr.getBytes();

        try {
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(1, deskey);
            return Base64.encodeBase64String(c1.doFinal(src));
        } catch (NoSuchAlgorithmException var6) {
            return "";
        } catch (NoSuchPaddingException var7) {
            return "";
        } catch (InvalidKeyException var8) {
            return "";
        } catch (BadPaddingException var9) {
            return "";
        } catch (IllegalBlockSizeException var10) {
            return "";
        }
    }

    public static String decrypt3Des(String key, String desStr) {
        Base64 base64 = new Base64();
        byte[] keybyte = hex(key);
        byte[] src = base64.decode(desStr);

        try {
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(2, deskey);
            return new String(c1.doFinal(src));
        } catch (NoSuchAlgorithmException var7) {
            return "";
        } catch (NoSuchPaddingException var8) {
            return "";
        } catch (Exception var9) {
            return "";
        }
    }

}
