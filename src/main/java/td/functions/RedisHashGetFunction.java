package td.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/30/0030.
 */
public class RedisHashGetFunction extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__RHashGet"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("database"));
        desc.add(JMeterUtils.getResString("hash")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("key")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable varName;
    private CompoundVariable database;
    private CompoundVariable hash;
    private CompoundVariable key;


    /** {@inheritDoc} */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {

        String db = database.execute().trim();
        String thekey = hash.execute().trim();
        String thefield = key.execute().trim();

        if (db ==null || db =="" || thekey ==null || thekey =="" || thefield ==null || thefield ==""){
            Jedis jedis = new Jedis("node.td-k8s.com",1379);
            jedis.auth("mWRK6joVy5No");
            jedis.connect();
            jedis.select(Integer.parseInt(db));


            String randString = jedis.hget(thekey,thefield);


            if (varName != null) {
                JMeterVariables vars = getVariables();
                final String varTrim = varName.execute().trim();
                if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                    vars.put(varTrim, randString);
                }
            }

            System.out.println(thekey+thefield);
            return randString;
        }else {
            return null;
        }



    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 3, 4);
        Object[] values = parameters.toArray();
        database = (CompoundVariable)values[0];
        hash = (CompoundVariable) values[1];
        key = (CompoundVariable) values[2];
        if (values.length>3){
            varName = (CompoundVariable) values[3];
        } else {
            varName = null;
        }

    }

    /** {@inheritDoc} */
    @Override
    public String getReferenceKey() {
        return KEY;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}
