/**
 * Created by linweili on 2018/8/30/0030.
 */
public class Substring128 {
    public static void main(String[] args){
        String userId = "B5F8DBEB-8375-42DA-A465-8D706811D996";
        String keyStr = userId.substring(userId.length() - 4);
        System.out.println((Integer.parseInt(keyStr, 16) % 32));
    }
}
