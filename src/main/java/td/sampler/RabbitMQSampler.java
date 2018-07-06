package td.sampler;

import com.drew.metadata.exif.PanasonicRawIFD0Descriptor;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by linweili on 2018/7/6/0006.
 */
public class RabbitMQSampler extends AbstractSampler implements TestStateListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQSampler.class);


    /**
     * 基本参数
     */
    private static final String Host = "node.td-k8s.com";
    private static final int Port = 5672;
    private static final String VirtualHost = "/globalBizEvent";
    private static final String Username = "admin";
    private static final String Password = "admin";
    private static final String  TYPE = "exchange";
    private static final String  TYPE_VALUE = "userRegisterExchange";
    private static final String MESSAGE = "message";



    public RabbitMQSampler(){
        //设置Sampler名称
        setName("RabbitMQ Sampler");
    }


    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        try {
            result.sampleStart();

            //广播
            Boolean status = Broadcast();
            System.out.println("广播结果："+status);

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

    private Boolean Broadcast() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost(Host);
        factory.setPort(Port);
        factory.setVirtualHost(VirtualHost);
        factory.setUsername(Username);
        factory.setPassword(Password);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        logger.info("连接mq:"+Host+","+VirtualHost+","+TYPE+","+TYPE_VALUE);
        //exchange的类型包括:direct, topic, headers and fanout,我们本例子主要关注的是fanout
        //fanout类型是指向所有的队列发送消息
        //以下是创建一个fanout类型的exchange,取名logs
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TYPE_VALUE, BuiltinExchangeType.FANOUT);

        String message = "{data}";

        //1.在上个"hello world"例子中,我们用的是channel.basicPublish("", "hello", null, message.getBytes());
        //这里用了默认的exchanges,一个空字符串 "",在basicPublish这个方法中,第一个参数即是exchange的名称
        //2.准备向我们命名的exchange发送消息啦
        channel.basicPublish(TYPE_VALUE, "", null, message.getBytes("UTF-8"));
        logger.info("推送消息:"+message);
        System.out.println("producer send:" + message);

        channel.close();
        connection.close();

        return true;
    }


    public String getMessage() {
        return getPropertyAsString(KAFKA_MESSAGE);
    }

    public void setMessage(String message) {
        setProperty(KAFKA_MESSAGE, message);
    }

    public String getMessageSerializer() {
        return getPropertyAsString(KAFKA_MESSAGE_SERIALIZER);
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


}
