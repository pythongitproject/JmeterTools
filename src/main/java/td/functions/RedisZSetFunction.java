package td.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.util.JMeterUtils;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/30/0030.
 */
public class RedisZSetFunction extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__RZSet"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("database"));
        desc.add(JMeterUtils.getResString("zset")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("score")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("value")); //$NON-NLS-1$
    }

    private CompoundVariable database;
    private CompoundVariable zset;
    private CompoundVariable score;
    private CompoundVariable value;


    /** {@inheritDoc} */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {


        String db = database.execute().trim();
        String thekey = zset.execute().trim();
        String thefield = score.execute().trim();
        String thevalue = value.execute().trim();

        if (db ==null || db =="" || thekey ==null || thekey =="" || thefield ==null || thefield =="" || thevalue ==null || thevalue ==""){
            return null;

        }else {

            Jedis jedis = new Jedis("node.td-k8s.com",1379);
            jedis.auth("mWRK6joVy5No");
            jedis.connect();
            jedis.select(Integer.parseInt(db));
            Long count = jedis.zadd(thekey, Double.parseDouble(thefield),thevalue);
            if(count>0){
                System.out.println("写入成功："+thekey+":"+thefield+":"+thevalue);
            }else {
                System.out.println("写入失败");
            }
            return count.toString();
        }

    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 4, 4);
        Object[] values = parameters.toArray();
        database = (CompoundVariable)values[0];
        zset = (CompoundVariable) values[1];
        score = (CompoundVariable) values[2];
        value = (CompoundVariable) values[3];

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
