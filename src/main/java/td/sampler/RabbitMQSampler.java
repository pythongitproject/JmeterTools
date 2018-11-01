package td.sampler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final String Port = "5672";
    private static final String VirtualHost = "/globalBizEvent";
    private static final String Username = "admin";
    private static final String Password = "admin";
    private static final String ExchangeType = "fanout";
    private static final String ExchangeName = "userRegisterExchange";
    private static final String Message = "message";
    private static final String QueueName = "hd_commonInvitesendTB";
    private static final String Routingkey = "hd.commonInvitesendTBQueue";
    private static final String Durable = "true";


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

            sendMQByType(result);

        } catch (Exception e) {
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), null);
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }
        return result;
    }

    private void sendMQByType(SampleResult result) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.getHost());
        if (this.getPort() != null) {
            factory.setPort(Integer.parseInt(this.getPort()));
        }
        if (this.getVirtualHost() != null) {
            factory.setVirtualHost(this.getVirtualHost());
        }
        factory.setUsername(this.getUsername());
        factory.setPassword(this.getPassword());
        StringBuilder MQ_MSG = new StringBuilder();


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        try {

            if("fanout".equals(this.getExchangeType().toLowerCase())){
                channel.exchangeDeclare(this.getExchangeName(), this.getExchangeType().toLowerCase());
                channel.queueDeclare(this.getExchangeName(), false, false, false, null);
                channel.basicPublish(this.getExchangeName(), "", null, this.getMessage().getBytes("UTF-8"));
                MQ_MSG.append("SendTime: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())).append("\n");
            }else if("direct".equals(this.getExchangeType().toLowerCase())) {
                channel.exchangeDeclare(this.getExchangeName(), this.getExchangeType(), Boolean.parseBoolean(this.getDurable()),false,null);
                channel.queueBind(this.getQueueName(), this.getExchangeName(), this.getQueueName());
                channel.basicPublish(this.getExchangeName(), this.getRoutingkey(), null, this.getMessage().getBytes("UTF-8"));
                MQ_MSG.append("SendTime: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())).append("\n");
            }else {
                channel.exchangeDeclare(this.getExchangeName(), this.getExchangeType().toLowerCase());
                channel.queueDeclare(this.getExchangeName(), false, false, false, null);
                channel.basicPublish(this.getExchangeName(), "", null, this.getMessage().getBytes("UTF-8"));
                MQ_MSG.append("SendTime: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())).append("\n");
            }
            result.sampleEnd();
            result.setSuccessful(true);
            MQ_MSG.append("Host: ").append(this.getHost()).append("\n")
                    .append("VirtualHost: ").append(this.getVirtualHost()).append("\n")
                    .append("ExchangeType: ").append(this.getExchangeType()).append("\n")
                    .append("ExchangeName: ").append(this.getExchangeName()).append("\n")
                    .append("QueueName: ").append(this.getQueueName()).append("\n")
                    .append("Routingkey：").append(this.getRoutingkey()).append("\n")
                    .append("Durable：").append(this.getDurable()).append("\n")
                    .append("Message: ").append(this.getMessage()).append("\n");
            result.setRequestHeaders(MQ_MSG.toString());
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
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("FAILED");
        }finally {
            channel.close();
            connection.close();
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

    public  String getVirtualHost() {
        return getPropertyAsString(VirtualHost);
    }

    public  void setVirtualHost(String virtualHost) {
        setProperty(VirtualHost,virtualHost);
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

    public  String getExchangeType() {
        return getPropertyAsString(ExchangeType);
    }

    public void setExchangeType(String exchangeType) {
        setProperty(ExchangeType, exchangeType);
    }

    public  String getExchangeName() {
        return getPropertyAsString(ExchangeName);
    }

    public void setExchangeName(String exchangeName) {
        setProperty(ExchangeName, exchangeName);
    }

    public String getMessage() { return getPropertyAsString(Message); }

    public void setMessage(String message) {setProperty(Message, message); }


    public void setQueueName(String queueName) {setProperty(QueueName, queueName); }

    public void setRoutingkey(String routingkey) {setProperty(Routingkey, routingkey); }

    public void setDurable(String durable) {setProperty(Durable, durable); }


    public  String getQueueName() {return getPropertyAsString(QueueName); }

    public  String getRoutingkey() {return getPropertyAsString(Routingkey); }

    public  String getDurable() { return getPropertyAsString(Durable); }

    @Override
    public void testStarted() {}

    @Override
    public void testStarted(String s) {}

    @Override
    public void testEnded() {}

    @Override
    public void testEnded(String s) {}


}
