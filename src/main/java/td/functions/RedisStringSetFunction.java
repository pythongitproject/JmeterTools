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
public class RedisStringSetFunction extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__RStringSet"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("database"));
        desc.add(JMeterUtils.getResString("key")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("value")); //$NON-NLS-1$
    }

    private CompoundVariable database;
    private CompoundVariable key;
    private CompoundVariable value;


    /** {@inheritDoc} */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {

        String db = database.execute().trim();
        String thefield = key.execute().trim();
        String thevalue = value.execute().trim();

        if (db ==null || db ==""|| thefield ==null || thefield =="" || thevalue ==null || thevalue =="") {
            return null;
        }else {

            Jedis jedis = new Jedis("node.td-k8s.com",1379);
            jedis.auth("mWRK6joVy5No");
            jedis.connect();
            jedis.select(Integer.parseInt(db));
            String randString = jedis.set(thefield,thevalue);

            System.out.println(randString);
            return randString;
        }


    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 3, 3);
        Object[] values = parameters.toArray();
        database = (CompoundVariable)values[0];
        key = (CompoundVariable) values[1];
        value = (CompoundVariable) values[2];

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
