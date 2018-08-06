import com.google.protobuf.Internal;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.functions.UserName;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class Testdsf {

    private static final String KEY_ALGORITHM = "AES";
    private static final String KEY_ALGORITHM_DESEDE = "DESede";

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content = "15866660001";
        String encryptKey = "thirdplatWebToFeifei20180727MSJDHDFHIERUI@U#$*%*#$@!@#(*(&*";


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
        System.out.println(Base64.encodeBase64String(result));



        }
    }

