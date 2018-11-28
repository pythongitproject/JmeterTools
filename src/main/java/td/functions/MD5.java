package td.functions;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class MD5 extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__MD5"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("param"));
        desc.add(JMeterUtils.getResString("size"));
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable param;
    private CompoundVariable size;
    private CompoundVariable varName;


    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {

            String max = param.execute().trim();
            String sizes = size.execute().trim();
        System.out.println("ff" + sizes + "dd");
            if(max==null || max ==""){
                return null;
            }else {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(max.getBytes());
                    byte b[] = md.digest();
                    int i;
                    StringBuffer buf = new StringBuffer("");
                    for (int offset = 0; offset < b.length; offset++) {
                        i = b[offset];
                        if (i < 0){
                            i += 256;
                        }
                        if (i < 16) {
                            buf.append("0");
                        }
                        buf.append(Integer.toHexString(i));
                    }

                    String md5res = "";
                    if(sizes.equals("big") || "".equals(sizes)){
                        md5res = buf.toString().toUpperCase();
                    }else if (sizes.equals("small")){
                        md5res = buf.toString().toLowerCase();
                    }else {
                        md5res = buf.toString().toUpperCase();
                    }

                    if (varName != null) {
                        JMeterVariables vars = getVariables();
                        final String varTrim = varName.execute().trim();
                        if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                            vars.put(varTrim, md5res);
                        }
                    }

                    return md5res;
                    //32位加密
//                    System.out.println("md5:"+buf.toString().toLowerCase());
                    // 16位的加密
                    //return buf.toString().substring(8, 24);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return null;
                }
            }


}

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 3);
        Object[] values = parameters.toArray();
        param = (CompoundVariable)values[0];
        size = (CompoundVariable)values[1];
        if (values.length > 2){
            varName = (CompoundVariable) values[2];
        } else {
            varName = null;
        }
    }
}
