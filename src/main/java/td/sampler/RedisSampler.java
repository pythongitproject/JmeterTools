package td.sampler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by linweili on 2018/7/6/0006.
 */
public class RedisSampler extends AbstractSampler implements TestStateListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisSampler.class);


    /**
     * 基本参数
     */
    private static final String Host = "node.td-k8s.com";
    private static final String Port = "1379";
    private static final String Password = "mWRK6joVy5No";
    private static final String Db = "5";
    private static final String Hash = "";
    private static final String Key = "";
    private static final String Value = "";


    public RedisSampler(){
        //设置Sampler名称
        setName("Redis Sampler");
    }

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        try {
            result.sampleStart();
            doRedis(result);

        } catch (Exception e) {
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }
        return result;
    }

    private void doRedis(SampleResult result){


        StringBuilder msg = new StringBuilder();
        try {
            Jedis jedis = new Jedis(this.getHost(), Integer.parseInt(this.getPort()));
            jedis.auth(this.getPassword());
            jedis.connect();
            jedis.select(Integer.parseInt(this.getDb()));

//            Long count = jedis.zadd(thekey, Double.parseDouble(thefield),thevalue);

            result.sampleEnd();
            result.setSuccessful(true);
            result.setRequestHeaders(msg.toString());
            result.setResponseData("MQ消息发送成功", "utf-8");
            result.setResponseCodeOK();

        } catch (Exception e) {
            e.printStackTrace();
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }

    }

    public  String getHost() {
        return getPropertyAsString(Host);
    }

    public  void setHost(String host) {
        setProperty(Host,host);
    }

    public String getPort() {
        return getPropertyAsString(Port);
    }

    public  void setPort(String port) {
        setProperty(Port,port);
    }
    public  String getPassword() {
        return getPropertyAsString(Password);
    }
    public  void setPassword(String password) {
        setProperty(Password,password);
    }
    public  String getDb() {
        return getPropertyAsString(Db);
    }
    public  void setDb(String db) {
        setProperty(Db,db);
    }
    public  String getHash() {
        return getPropertyAsString(Hash);
    }
    public  void setHash(String hash) {
        setProperty(Hash,hash);
    }

    public  String getKey() {
        return getPropertyAsString(Key);
    }
    public  void setKey(String key) {
        setProperty(Key,key);
    }

    public  String getValue() {
        return getPropertyAsString(Value);
    }
    public  void setValue(String value) {
        setProperty(Value,value);
    }

    @Override
    public void testStarted() {}

    @Override
    public void testStarted(String s) {}

    @Override
    public void testEnded() {}

    @Override
    public void testEnded(String s) {}


}
