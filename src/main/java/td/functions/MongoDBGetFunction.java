package td.functions;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.bson.Document;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/30/0030.
 */
public class MongoDBGetFunction extends AbstractFunction{

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__MongoGet"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("phone")); //$NON-NLS-1$
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable varName;
    private CompoundVariable phone;


    /** {@inheritDoc} */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        String thefield = phone.execute().trim();

        if (thefield ==null || thefield =="") {

            return null;
        }else {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("10.100.11.82", 27017);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            MongoCredential credential = MongoCredential.createScramSha1Credential("root", "sheng_cai_zhi_dao", "a123456a".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient(addrs, credentials);

            //连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("sheng_cai_zhi_dao");
            System.out.println("Connect to database successfully");
            MongoCursor<Document> dd = mongoDatabase.getCollection("sheng_cai_zhi_dao").find(new BasicDBObject("phone", thefield)).sort(new BasicDBObject("add_date", -1)).limit(1).iterator();
            String code = "";
            if (dd.hasNext()) {
                Document sd = dd.next();
                System.out.println(sd.toJson());
                String target = sd.toJson();
                JSONObject jsStr = new JSONObject(target);
                code = jsStr.get("verify_code").toString();
                if (varName != null) {
                    JMeterVariables vars = getVariables();
                    final String varTrim = varName.execute().trim();
                    if (vars != null && varTrim.length() > 0){// vars will be null on TestPlan
                        vars.put(varTrim, code);
                    }
                }
            }
            else {
                System.out.println("查询为空");
            }
            return code;

        }

    }

    /** {@inheritDoc} */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 2);
        Object[] values = parameters.toArray();
        phone = (CompoundVariable) values[0];
        if (values.length>1){
            varName = (CompoundVariable) values[1];
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
