package td.sampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;

/**
 * Created by linweili on 2018/9/12/0012.
 */
public class MongoDBSampler extends AbstractSampler implements TestStateListener {

    private static final String Host = "10.100.11.82";
    private static final String Port = "27017";
    private static final String Username = "root";
    private static final String Password = "a123456a";
    private static final String Db = "sheng_cai_zhi_dao";
    private static final String Script = "message";

    public MongoDBSampler(){
        //设置Sampler名称
        setName("MongoDB Sampler");
    }

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        try {
            result.sampleStart();




            result.sampleEnd();
            result.setSuccessful(true);
            result.setResponseCodeOK();
        } catch (Exception e) {
            result.sampleEnd(); // stop stopwatch
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }

        return result;
    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String s) {

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


    public  String getUsername() {
        return getPropertyAsString(Username);
    }

    public  void setUsername(String username) {
        setProperty(Username,username);
    }

    public String getPassword() {
        return getPropertyAsString(Password);
    }

    public void setPassword(String password) {
        setProperty(Password, password);
    }


    public  String getDb() {
        return getPropertyAsString(Db);
    }

    public void setDb(String exchangeName) {
        setProperty(Db, exchangeName);
    }

    public String getScript() {
        return getPropertyAsString(Script);
    }

    public void setScript(String message) {
        setProperty(Script, message);
    }

}
