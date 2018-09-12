import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.limit;

/**
 * Created by linweili on 2018/9/12/0012.
 */
public class TestMongodb {

    public static void main(String[] args) {
        //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("10.100.11.82", 27017);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);

        MongoCredential credential = MongoCredential.createScramSha1Credential("root", "sheng_cai_zhi_dao", "a123456a".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addrs, credentials);

        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("sheng_cai_zhi_dao");
        System.out.println("Connect to database successfully");
        MongoCursor<Document> dd = mongoDatabase.getCollection("user_send_sms_log").find(new BasicDBObject("phone", "15913140138")).sort(new BasicDBObject("add_date", -1)).limit(1).iterator();
        if (dd.hasNext()) {
            Document sd = dd.next();
            System.out.println(sd.toJson());
            String target = sd.toJson();
            JSONObject jsStr = new JSONObject(target);
            System.out.println(jsStr.get("verify_code"));
        }
        else {
            System.out.println("查询为空");
        }
    }
}
