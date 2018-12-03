package td.functions;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DECFunciton extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__DEC"; //$NON-NLS-1$

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
            String aespassword = "12345678";
            EncryptUtil en  = new EncryptUtil();
            String res = en.decrypt(s, aespassword);
            if (varName != null) {
                JMeterVariables vars = getVariables();
                final String varTrim = varName.execute().trim();
                if (vars != null && varTrim.length() > 0){
                    vars.put(varTrim, res);
                }
            }
            return res;
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
