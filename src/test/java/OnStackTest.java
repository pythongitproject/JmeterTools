/**
 * Created by linweili on 2018/8/9/0009.
 */
public class OnStackTest {
    public static class User{
        int id = 0;
        String name = "";
    }

    public static User u;

    public static void alloc(){
//        User u = new User();
        u = new User();
        u.id = 5;
        u.name = "jim";
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e-b);
    }
}
