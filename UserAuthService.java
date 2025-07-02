import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Properties;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UserAuthService {
   private MongoCollection<Document> userCollection;

   public UserAuthService() {
      try {
         Properties props = new Properties();
         InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties");

         try {
            if (input == null) {
               throw new RuntimeException("config.properties not found!");
            }

            props.load(input);
         } catch (Throwable var8) {
            if (input != null) {
               try {
                  input.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (input != null) {
            input.close();
         }

         String uri = props.getProperty("mongo.uri");
         String dbName = props.getProperty("mongo.database");
         String collection = props.getProperty("mongo.collection");
         MongoClient client = MongoClients.create(uri);
         MongoDatabase database = client.getDatabase(dbName);
         this.userCollection = database.getCollection(collection);
         System.out.println("Connected to MongoDB!");
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new RuntimeException("MongoDB initialization failed: " + var9.getMessage());
      }
   }

   public boolean signup(String email, String password) {
      if (this.userCollection.find(Filters.eq("email", email)).first() != null) {
         return false;
      } else {
         String hashed = this.hashPassword(password);
         Document user = (new Document("email", email)).append("password", hashed);
         this.userCollection.insertOne(user);
         return true;
      }
   }

   public boolean login(String email, String password) {
      String hashed = this.hashPassword(password);
      Document user = (Document)this.userCollection.find(Filters.and(new Bson[]{Filters.eq("email", email), Filters.eq("password", hashed)})).first();
      return user != null;
   }

   private String hashPassword(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(password.getBytes());
         StringBuilder hex = new StringBuilder();
         byte[] var5 = hash;
         int var6 = hash.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            byte b = var5[var7];
            hex.append(String.format("%02x", b));
         }

         return hex.toString();
      } catch (Exception var9) {
         throw new RuntimeException("Password hashing failed", var9);
      }
   }
}
