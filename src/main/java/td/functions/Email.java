package td.functions;

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
import java.util.Random;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class Email extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__Email"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable varName;


    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String[] end = {"@163.com","@qq.com","@tuandai.com"};
        String start = "ghabqcdefijhtrshsdfsfaarehndfauyksrtertwqazweerfef".substring((int)(Math.random()*5),(int)(Math.random()*5+15))+new Random().nextInt(10000)+new Random().nextInt(100);
        String email = start + end[new Random().nextInt(3)];
        System.out.println("email:"+email);
        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                vars.put(varTrim, email);
            }
        }
        return email;
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
