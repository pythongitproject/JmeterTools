import com.google.protobuf.Internal;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.functions.UserName;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class Testdsf {

    private static final Logger logger = LoggerFactory.getLogger(Testdsf.class);
    private static final String Host = "node.td-k8s.com";
    private static final String Port = "5672";
    private static final String VirtualHost = "/globalBizEvent";
    private static final String Username = "admin";
    private static final String Password = "admin";
    private static final String ExchangeType = "fanout";
    private static final String ExchangeName = "globalBizEventWeExchage";
    private static final String Message = "message";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //创建一个新的连接
        factory.setHost(Host);
        factory.setPort(Integer.parseInt(Port));
        factory.setVirtualHost(VirtualHost);
        factory.setUsername(Username);

        factory.setPassword(Password);
        Connection connection = factory.newConnection();
        //exchange的类型包括:direct, topic, headers and fanout,我们本例子主要关注的是fanout
        //fanout类型是指向所有的队列发送消息
        //以下是创建一个fanout类型的exchange,取名logs
        Channel channel = connection.createChannel();



        channel.exchangeDeclare(ExchangeName, ExchangeType.toLowerCase());

        String message = "{\n" +
                "\t\"signInfo\": {\n" +
                "\t\t\"userId\": \"BC397BD2-867F-475D-B5D0-A17B64DC2B1E\",\n" +
                "\t\t\"signDate\": \"2018-05-18\",\n" +
                "\t\t\"type\": 1,\n" +
                "\t\t\"continuousSignedDays\": 5\n" +
                "\t}\n" +
                "}";

        //1.在上个"hello world"例子中,我们用的是channel.basicPublish("", "hello", null, message.getBytes());
        //这里用了默认的exchanges,一个空字符串 "",在basicPublish这个方法中,第一个参数即是exchange的名称
        //2.准备向我们命名的exchange发送消息啦
        channel.basicPublish(ExchangeName, "", null, message.getBytes("UTF-8"));
        channel.close();
        connection.close();
        }
    }

