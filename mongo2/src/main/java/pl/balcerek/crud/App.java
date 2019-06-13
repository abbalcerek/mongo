package pl.balcerek.crud;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class App {

    private static final String MONGO_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "my_db";

    private static MongoClient client = null;

    public static void main(String[] args) throws UnknownHostException  {

//        List<String> users = new LinkedList<>();
//        for (int i = 0; i < 10000; i++) {
//            users.add();
//        }

        saveUser("user",
                new Person(null, "tim", 20),
                new Person(null, "bob", 11)
        );

        List<Person> result = findUser("user", new BasicDBObject());
        result.forEach(System.out::println);
    }

    public static void saveUser(String collection, Person... object) throws UnknownHostException {
        DB db = initializeOrGetDB(DATABASE_NAME);
        db.getCollectionFromString(collection)
                .insert(Arrays.stream(object).map(App::toDBObject).collect(Collectors.toList()));
    }

    public static void updateUser(String collection, DBObject object) {

    }

    public static void deleteUser(String collection, DBObject object) {

    }

    public static List<Person> findUser(String collection, DBObject object) throws UnknownHostException {
        DB db = initializeOrGetDB(DATABASE_NAME);

        Iterable<DBObject> iter = db.getCollectionFromString(collection).find();
        return StreamSupport.stream(iter.spliterator(), true).peek(p -> System.out.println(p))
                .map(App::fromDBObject).collect(Collectors.toList());
    }

    public static void createPeople() {

    }

    public static DB initializeOrGetDB(String dbName) throws UnknownHostException {
        if (client == null)
            client = new MongoClient(new MongoClientURI(MONGO_URL));

        return client.getDB(dbName);
    }

    @AllArgsConstructor
    @Data
    public static class Person {

        private ObjectId _id;
        private String name;
        private int age;

    }

    private static DBObject toDBObject(Person person) {
        Gson gson = new Gson();
        return  (BasicDBObject) JSON.parse(gson.toJson(person));
    }

    private static Person fromDBObject(DBObject object) {
        Gson gson = new Gson();
        System.out.println(object.toMap());
        System.out.println("id: " + object.get("_id").getClass().getName());
        JsonElement jsonElement = gson.toJsonTree(object.toMap());
        return gson.fromJson(jsonElement, Person.class);
    }


}
