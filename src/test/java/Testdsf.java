import com.google.protobuf.Internal;
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
    private static final String  QUEUE_NAME = "jmeter_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("node.td-k8s.com");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        logger.info("连接mq:"+"node.td-k8s.com");
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息到队列中
        String message = "Hello MQ";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        logger.info("推送消息:"+message);
        System.out.println("producer send:" + message);
        //关闭通道及连接
        channel.close();
        connection.close();
        logger.info("mq已断开:"+message);
    }
}
