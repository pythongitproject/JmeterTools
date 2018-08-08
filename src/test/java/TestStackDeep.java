/**
 * Created by linweili on 2018/8/8/0008.
 * 由于递归没有出口，这段代码可能会抛出栈溢出错误，在抛出栈溢出错误时，打印最大的调用深度
 * Run As-->run Configurations....设置Vm arguments
 * -Xss128K
 * -Xss256K
 */
public class TestStackDeep {
    private static int count =0;
    static void recursion(){
        count++;
        recursion();
    }
    public static void main(String[] args){

        try {
            recursion();
        }catch (Throwable throwable){
            System.out.println("deep of calling=" + count);
            throwable.printStackTrace();
        }
    }

}
