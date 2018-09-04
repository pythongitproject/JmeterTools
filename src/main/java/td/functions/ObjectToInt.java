package td.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class ObjectToInt extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__ToInt"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("param"));
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable param;
    private CompoundVariable varName;


    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {

            String s = param.execute().trim();
            if(s==null || s ==""){
                return null;
            }else {
                    Number num = Float.parseFloat(s);
                    int ii = num.intValue();

                    if (varName != null) {
                        JMeterVariables vars = getVariables();
                        final String varTrim = varName.execute().trim();
                        if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                            vars.put(varTrim, Integer.toString(ii));
                        }
                    }
                return Integer.toString(ii);
            }

}

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 2);
        Object[] values = parameters.toArray();
        param = (CompoundVariable)values[0];
        if (values.length>1){
            varName = (CompoundVariable) values[1];
        } else {
            varName = null;
        }
    }
}
