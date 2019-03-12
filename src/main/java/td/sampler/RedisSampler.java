package td.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class RedisSampler extends AbstractSampler{
    private static final long serialVersionUID = 240L;

    private static final Logger log = LoggerFactory.getLogger(RedisSampler.class);

    // The name of the property used to hold our data
    public static final String host = "host.text";
    public static final String port = "port.text";
    public static final String pwd = "pwd.text";
    public static final String db = "db.text";
    public static final String rtype = "rtype.text";
    public static final String rdotype = "rdotype.text";
    public static final String hash = "hash.text";
    public static final String key = "key.text";
    public static final String valueContent = "valueContent.text";

    private static AtomicInteger classCount = new AtomicInteger(0); // keep track of classes created


    private String getTitle() {
        return this.getName();
    }

    /**
     * @return the data for the sample
     */
    public String gethost() {
        return getPropertyAsString(host);
        //从gui获取host输入的数据
    }

    public String getport() {
        return getPropertyAsString(port);
        //从gui获取port输入的数据
    }

    public String getpwd() {
        return getPropertyAsString(pwd);

    }

    public String getdb() {
        return getPropertyAsString(db);

    }

    public String getrtype() {
        return getPropertyAsString(rtype);

    }

    public String getrdotype() {
        return getPropertyAsString(rdotype);

    }

    public String gethash() {
        return getPropertyAsString(hash);

    }

    public String getkey() {
        return getPropertyAsString(key);

    }

    public String getvalueContent() {
        return getPropertyAsString(valueContent);

    }


    public RedisSampler() {
        //getTitle方法会调用getName方法，setName不写会默认调用getStaticLabel返回的name值
        setName("Redis Sampler");
        classCount.incrementAndGet();
        trace("FirstPluginSampler()");
    }
    private void trace(String s) {
        String tl = getTitle();
        String tn = Thread.currentThread().getName();
        String th = this.toString();
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

        log.info("host: {}" ,this.gethost());
        log.info("post: {}" ,this.getport());
        log.info("pwd: {}" ,this.getpwd());
        log.info("rtype: {}" ,this.getrtype());
        log.info("rdotype: {}" ,this.getrdotype());
        log.info("hash: {}" ,this.gethash());
        log.info("key: {}" ,this.getkey());
        log.info("value: {}" ,this.getvalueContent());

        StringBuilder msg = new StringBuilder();
        try {
//            Jedis jedis = new Jedis(this.getHost(), Integer.parseInt(this.getPort()));
//            jedis.auth(this.getPassword());
//            jedis.connect();
//            jedis.select(Integer.parseInt(this.getDb()));
//
////            Long count = jedis.zadd(thekey, Double.parseDouble(thefield),thevalue);

            result.sampleEnd();
            result.setSuccessful(true);
            result.setRequestHeaders(msg.toString());
            result.setResponseData("执行完毕", "utf-8");
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

}