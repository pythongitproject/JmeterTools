import com.google.protobuf.Internal;
import org.apache.commons.codec.digest.DigestUtils;
import td.functions.UserName;

import java.util.Random;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class Testdsf {
    public static void main(String[] args){
        String str = "123456"+"123456"+"1528960439497";
        String sign = DigestUtils.md5Hex("1234561234561528960624172");
        System.out.println(sign);
        //993575f69932bc2d2a8e22d16c1bd29d
        System.out.println(new Random().nextInt(2));
        String bankCard = "955880120291";
        String cardCode1 = null;
        cardCode1 = new Random().nextInt(899999) + 1000000 + "";
        bankCard = bankCard + cardCode1;
        System.out.println(bankCard);
        String[] telFirst="19922,16633".split(",");
        String first=telFirst[new Random().nextInt(2)];
        String second= new Random().nextInt(89999)+100000+"";

        System.out.println("telno:"+first+second);
//        for (int i=1;i<100;i++){
            String[] end = {"@163.com","@qq.com","@tuandai.com"};
            String start = "ghabqcdefijhtrshsdfsfaarehndfauyksrtertwqazweerfef".substring((int)(Math.random()*5),(int)(Math.random()*5+15))+new Random().nextInt(10000)+new Random().nextInt(100);
            String email = start + end[new Random().nextInt(3)];
            System.out.println("email:"+email);
//        }
        System.out.println(new UserName().getXing());

    }
}
