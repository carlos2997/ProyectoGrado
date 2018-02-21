
package SpatialData;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;
import org.json.JSONObject;

/**
 *
 * @author carlos
 */
public class Main {
    
    static DB myDatabase;
    static SpatialQueryMongo sqMongo;
    
    public static void main(String[] args) throws Throwable{
        
        MongoClientURI uri = new MongoClientURI("mongodb://10.8.0.23:27017");
        
        MongoClient mongoClient = new MongoClient(uri);

        myDatabase = mongoClient.getDB("test");
        
        DBCollection collection = myDatabase.getCollection("hola");
        
        BasicDBObject document = new BasicDBObject();
        document.put("database", "mkyongDB");
        document.put("table", "hosting");
        
        collection.insert(document);
        
//        sqMongo = new SpatialQueryMongo();
        
//        
//        
//        double[] coordenadasPunto =  {4.886684417724609,52.36627132364143};
//        
//        String nameLand = null;
//        
//        DBCursor cursor = collection.find(sqMongo.queryPointInPolygon(coordenadasPunto));
//        try {
//            while(cursor.hasNext()) {
//                DBObject cur = cursor.next();
//                System.out.println(cur);
//                JSONObject output = new JSONObject(JSON.serialize(cur));
//                nameLand = output.getJSONObject("polygons").getString("nameField");
//            }
//        } finally {
//           cursor.close();
//        }
//        
//        DBCollection collection1 = myDatabase.getCollection("fs.files");
//        
//        DBCursor cursor1 = collection1.find(sqMongo.queryPolygonPictures(nameLand));
//        try {
//            while(cursor1.hasNext()) {
//                DBObject cur1 = cursor1.next();
//                System.out.println(cur1);
//            }
//        } finally {
//           cursor1.close();
//        }
//        
        
    }
}
