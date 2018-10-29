import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQTest {
    public static void main(String[] args) throws IOException, TimeoutException {

        String Host = "node.td-k8s.com";
        String Port = "5672";
        String VirtualHost = "/globalBizEvent";
        String Username = "admin";
        String Password = "admin";
        String ExchangeType = "direct";
        String ExchangeName = "hd_commonInvitesendTB";
        String Message = "{\n" +
                "  \"id\": \"2C91A512-B443-4710-A444-BCFF3EDC4324\",\n" +
                "  \"userId\": \"B55894D7-9713-4397-A891-F3A98A3B0E51\",\n" +
                "  \"amount\": 66650,\n" +
                "  \"yearRate\": 12.6,\n" +
                "  \"deadLine\": 3,\n" +
                "  \"orderDate\": \"2018-10-26T15:09:00\",\n" +
                "  \"deadType\": 1,\n" +
                "  \"productName\": \"csjyj\",\n" +
                "  \"projectType\": 59,\n" +
                "  \"productType\": 1,\n" +
                "  \"inviteStatus\": 2,\n" +
                "  \"interestAmount\": 562.3\n" +
                "}";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Host);
        factory.setPort(Integer.parseInt(Port));
        factory.setVirtualHost(VirtualHost);
        factory.setUsername(Username);
        factory.setPassword(Password);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ExchangeName, ExchangeType,true,false,null);
//        channel.exchangeDeclare(ExchangeName, ExchangeType);
        channel.queueDeclare(ExchangeName, false, false, false, null);
        channel.queueBind("hd_commonInvitesendTB", ExchangeName, "hd_commonInvitesendTB");
        String message = Message;
        channel.basicPublish(ExchangeName, "hd.commonInvitesendTBQueue", null, message.getBytes("UTF-8"));
        channel.close();
        connection.close();


    }
}
