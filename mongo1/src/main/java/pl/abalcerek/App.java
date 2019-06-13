package pl.abalcerek;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String DB_NAME = "first_db1";
    private static final String MONGO_URL = "mongodb://localhost:27017";

    public static void main(String[] args) throws UnknownHostException {

        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URL));
        DB db = mongoClient.getDB(DB_NAME);
        DBCollection collection = db.getCollection("TheCollection");


        List<Integer> books = Arrays.asList(27464, 747854);
        DBObject person = new BasicDBObject()
                .append("name", "Jo Bloggs")
                .append("uid", "1")
                .append("address", new BasicDBObject("street", "123 Fake St")
                        .append("city", "Faketon")
                        .append("state", "MA")
                        .append("zip", 12345))
                .append("books", books);

        collection.insert(person);

        System.out.println(collection.find(new BasicDBObject().append("uid", "1")).one());

        System.out.println(collection.find(new BasicDBObject().append("address",
                new BasicDBObject()
                        .append("state", "MA")))
                .one());

        System.out.println(collection.find(new BasicDBObject().append("address.state",
                new BasicDBObject()
                        .append("state", "MA")))
                .one());


//        System.out.println(collection.find(new BasicDBObject()).one());

        System.out.println(new BasicDBObject().append("key", "value"));
        System.out.println(new BasicDBObject("kay", "value").append("key1", "value1"));

    }

}
