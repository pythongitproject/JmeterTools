package td.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by linweili on 2018/7/5/0005.
 */
public class MyPluginSampler extends AbstractSampler {
    private static final long serialVersionUID = 240L;

    private static final Logger log = LoggingManager.getLoggerForClass();

    // The name of the property used to hold our data
    public static final String DOMAIN = "domain.text";
    public static final String PORT = "port.text";
    public static final String USERNAME = "username.text";
    public static final String PWD = "pwd.text";
    public static final String TYPE = "type.text";
    public static final String postBodyContent = "postBodyContent.text";
    public static final String useKeepAlive = "useKeepAlive.text";

    private static AtomicInteger classCount = new AtomicInteger(0); // keep track of classes created


    private String getTitle() {
        return this.getName();
    }

    /**
     * @return the data for the sample
     */
    public String getdomain() {
        return getPropertyAsString(DOMAIN);
        //从gui获取domain输入的数据
    }

    public String getport() {
        return getPropertyAsString(PORT);
        //从gui获取port输入的数据
    }

    public String getUserName() {
        return getPropertyAsString(USERNAME);

    }

    public String getpath() {
        return getPropertyAsString(PWD);

    }

    public String getmethod() {
        return getPropertyAsString(TYPE);

    }

    public String getpostBodyContent() {
        return getPropertyAsString(postBodyContent);

    }

    public String getuseKeepAlive() {
        return getPropertyAsString(useKeepAlive);

    }

    public MyPluginSampler() {
        //getTitle方法会调用getName方法，setName不写会默认调用getStaticLabel返回的name值
        /**
         * 默认标题名称
         */
        setName("默认标题名称");
        classCount.incrementAndGet();
        trace("FirstPluginSampler()");
    }
    private void trace(String s) {
        String tl = getTitle();
        String tn = Thread.currentThread().getName();
        String th = this.toString();
        log.debug(tn + " (" + classCount.get() + ") " + tl + " " + s + " " + th);
    }

    @Override
    public SampleResult sample(Entry arg0) {
        // TODO Auto-generated method stub
        trace("sample()");
        SampleResult res = new SampleResult();
        boolean isOK = false; // Did sample succeed?

        String response = null;
        String sdomain = getdomain(); // Sampler data
        String sport = getport();
        String suserName = getUserName();
        String spath = getpath();
        String smethod = getmethod();
        String spostBodyContent = getpostBodyContent();
        String suseKeepAlive = getuseKeepAlive();

        res.setSampleLabel(getTitle());
            /*
             * Perform the sampling
             */
        res.sampleStart(); // Start timing
        try {

            // Do something here ...

            response = Thread.currentThread().getName();

                /*
                 * Set up the sample result details
                 */
            res.setSamplerData("setSamplerData!!!");
            res.setResponseData(response+sdomain+sport+suserName+spath+smethod+spostBodyContent+suseKeepAlive, null);
            res.setDataType(SampleResult.TEXT);

            res.setResponseCodeOK();
            res.setResponseMessage("OK");// $NON-NLS-1$
            isOK = true;
        } catch (Exception ex) {
            log.debug("", ex);
            res.setResponseCode("500");// $NON-NLS-1$
            res.setResponseMessage(ex.toString());
        }
        res.sampleEnd(); // End timimg

        res.setSuccessful(isOK);

        return res;
    }

}
