package td.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class IDCard extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__IDCard"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable varName;


    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String[] IDCard = getIDCard();
        System.out.println("IDCard:"+IDCard[0]);
        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                vars.put(varTrim, IDCard[0]);
            }
        }
        return IDCard[0];
    }
    private static String[] getIDCard() {
        // 18位身份证号码各位的含义:
        // 1-2位省、自治区、直辖市代码；
        // 3-4位地级市、盟、自治州代码；
        // 5-6位县、县级市、区代码；
        // 7-14位出生年月日，比如19670401代表1967年4月1日；
        // 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数；
        // 18位为校验码，0-9和X。
        // 作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，
        // 计算的结果是0-10，如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，
        // 因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10
        String id = "";
        // 随机生成省、自治区、直辖市代码 1-2
        String[] provinces = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37",
                "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
                "81", "82" };
        String province = provinces[new Random().nextInt(provinces.length - 1)];
        // 随机生成地级市、盟、自治州代码 3-4
        String[] citys = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "21", "22", "23", "24", "25",
                "26", "27", "28" };
        String city = citys[new Random().nextInt(citys.length - 1)];
        // 随机生成县、县级市、区代码 5-6
        String[] countys = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38" };
        String county = countys[new Random().nextInt(countys.length - 1)];
        // 随机生成出生年月 7-14
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dft1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - new Random().nextInt(365 * 100));
        //获取生日日期
        String birthday=dft1.format(date.getTime());
        System.out.println(birthday);
        String birth = dft.format(date.getTime());
        String year = birth.substring(0,4);
        if(Integer.parseInt(year)>1997){
            birth = birth.replace(year,"1995");
        }
        System.out.println(birth);
        // 随机生成顺序号 15-17
        String no = new Random().nextInt(899)+100 + "";

        //判断性别，2为女，1为男
        String sex=no.substring(no.length()-1);
        if(Integer.parseInt(sex)%2==0){
            sex="2";
        }else{
            sex="1";
        }
        // 生成前17位
        id = province + city + county + birth + no;
        String check = getCheckCode(id);
        // 拼接身份证号码
        id = province + city + county + birth + no + check;
        String[] cardInfo={id,sex,birthday};

        return cardInfo;
    }

    private static String getCheckCode(String number) {
	/*
	 * 第十八位数字的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2
	 * 1 6 3 7 9 10 5 8 4 2 2.将这17位数字和系数相乘的结果相加。 3.用加出来和除以11，看余数是多少？
	 * 4余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5
	 * 4 3 2。 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 *
	 */
        System.out.println(number);
        final int[] coef = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        final int[] mapping = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] store = new int[18];
        String str = null;
        if (number.length() == 17) {

            // 取出17位的每一个值
            for (int i = 0; i < number.length(); i++) {
                String s = number.substring(i, i + 1);
                store[i] = Integer.parseInt(s);
            }

            // 乘以不同的系数，将这17位数字和系数相乘的结果相加
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                sum = sum + store[i] * coef[i];
            }

            // 用加出来和除以11，看余数是多少？
            int remaining = sum % 11;
            // remaining = 2:'X'?
            if (remaining == 2) {
                str = "X";
            } else {
                str = Integer.toString(mapping[remaining]);
            }
        } else {
            System.out.println("位数不对，出错！");
        }
        return str;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 0, 1);
        Object[] values = parameters.toArray();
        if (values.length>0){
            varName = (CompoundVariable) values[0];
        } else {
            varName = null;
        }
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}
