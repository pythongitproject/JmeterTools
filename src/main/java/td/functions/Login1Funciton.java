package td.functions;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Login1Funciton extends AbstractFunction {

    private static final String SECRET = "~#^&tuandai*%#junte#111!";
    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__tuandaiw"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("param"));
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable param;
    private CompoundVariable varName;

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String s = param.execute().trim();
        if(StringUtils.isBlank(s)){
            return null;
        }else {
            String timeValue = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
            String userValue = s + "|" + timeValue + "|" + "-Chrome";
            String a1 = Cryptography.tripleDESEncrypt(userValue, SECRET);
            try {
                String fn = URLEncoder.encode(a1, "UTF-8");
                if (varName != null) {
                    JMeterVariables vars = getVariables();
                    final String varTrim = varName.execute().trim();
                    if (vars != null && varTrim.length() > 0){
                        vars.put(varTrim, fn);
                    }
                }
                return fn;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        checkParameterCount(collection, 1, 2);
        Object[] values = collection.toArray();
        param = (CompoundVariable)values[0];
        if (values.length>1){
            varName = (CompoundVariable) values[1];
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
