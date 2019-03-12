package td.sampler;

import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private SampleResult normalResult(String msg){
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.sampleEnd();
        result.setSuccessful(false);
        result.setResponseData(msg, "utf-8");
        result.setResponseCode("FAILED");
        return result;
    }

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        try {
            result.sampleStart();
            StringBuilder msg = new StringBuilder();
            String shost = gethost();
            String sport = getport();
            String spwd = getpwd();
            String sdb = getdb();
            String srtype = getrtype();
            String srdotype = getrdotype();
            String shash = gethash();
            String skey = getkey();
            String svalue = getvalueContent();
            msg.append("host:").append(shost).append("\n")
                    .append("port:").append(sport).append("\n")
                    .append("pwd:").append(spwd).append("\n")
                    .append("db:").append(sdb).append("\n")
                    .append("类型:").append(srtype).append("\n")
                    .append("操作:").append(srdotype).append("\n")
                    .append("hash:").append(shash).append("\n")
                    .append("key:").append(skey).append("\n")
                    .append("value:").append(svalue).append("\n");
            if(StringUtils.isBlank(shost) || StringUtils.isBlank(sport)||
                    StringUtils.isBlank(spwd)|| StringUtils.isBlank(sdb)){
                return normalResult("redis连接串不能为空");
            }

            Jedis jedis = new Jedis(shost.trim(), Integer.parseInt(sport.trim()));
            jedis.auth(spwd.trim());
            log.info("connect to {}",shost);
            jedis.connect();
            log.info("{} connected",shost);
            jedis.select(Integer.parseInt(sdb.trim()));

            if("String".equals(srtype)){
                if("GET".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim())){
                        return normalResult("key不能为空");
                    }
                    String res = jedis.get(skey.trim());
                    result.setResponseData(res,"utf-8");
                }
                if("SET".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim()) || StringUtils.isBlank(svalue.trim())){
                        return normalResult("key或value不能为空");
                    }
                    String res = jedis.set(skey.trim(),svalue.trim());
                    result.setResponseData(res,"utf-8");
                }
                if("DEL".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim())){
                        return normalResult("key不能为空");
                    }
                    Long count = jedis.del(skey.trim());
                    result.setResponseData(count.toString(),"utf-8");
                }
            }
            if("Hash".equals(srtype)){
                if("GET".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim())){
                        return normalResult("key不能为空");
                    }
                    String res = jedis.hget(shash.trim(),skey.trim());
                    result.setResponseData(res,"utf-8");
                }
                if("SET".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim()) || StringUtils.isBlank(svalue.trim())){
                        return normalResult("key或value不能为空");
                    }
                    Long count = jedis.hset(shash.trim(),skey.trim(),svalue.trim());
                    result.setResponseData(count.toString(),"utf-8");
                }
                if("DEL".equals(srdotype)){
                    if(StringUtils.isBlank(skey.trim())){
                        return normalResult("key不能为空");
                    }
                    Long count = jedis.hdel(shash.trim(),skey.trim());
                    result.setResponseData(count.toString(),"utf-8");
                }
            }
//            if("Set".equals(srtype)){
//                if("GET".equals(srdotype)){
//                    String res = jedis.set(shash.trim(),skey.trim());
//                    result.setResponseData(res,"utf-8");
//                }
//                if("SET".equals(srdotype)){
//                    Long count = jedis.hset(shash.trim(),skey.trim(),svalue.trim());
//                    result.setResponseData(count.toString(),"utf-8");
//                }
//                if("DEL".equals(srdotype)){
//                    Long count = jedis.hdel(shash.trim(),skey.trim());
//                    result.setResponseData(count.toString(),"utf-8");
//                }
//            }
//            if("ZSet".equals(srtype)){
//
//            }

            result.sampleEnd();
            result.setSuccessful(true);
            result.setRequestHeaders(msg.toString());
            result.setResponseCodeOK();
            return result;

        } catch (Exception e) {
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(SampleResult.TEXT);
            result.setResponseCode("FAILED");
            return result;
        }

    }

}