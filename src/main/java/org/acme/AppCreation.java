package org.acme;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.acme.model.Applications;
import org.bson.Document;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Path("/AppCreation")
public class AppCreation {
    @Inject MongoClient mongoClient;

    private MongoCollection getCollection(){
        return mongoClient.getDatabase("IntelliFlow").getCollection("apps");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String FetchAllApps() {
        Gson gson = new Gson();
        String formdata="";

               MongoCursor<Document> cursor = getCollection().find().iterator();
                       List<Applications> list = new ArrayList<>();
  try {
           while (cursor.hasNext()) {
               Document document = cursor.next();
               Applications forms = new Applications();
               forms.setName(document.getString("name"));
               forms.setDescription(document.getString("description"));
               list.add(forms);
           }
       } finally {
           cursor.close();
       }
        String jsonCartList = gson.toJson(list);

       return jsonCartList ;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String AddDocument(String inputdata) {
        System.out.println(inputdata);
        Gson gson = new Gson();
try {
    Applications data = gson.fromJson(inputdata, Applications.class);
    UpdateOptions options = new UpdateOptions();
    options.upsert(true);

 getCollection().updateOne(Filters.eq("name",  data.getName()), Updates.set("description",  data.getDescription()),options);

 return"";

} catch (Exception e) {
    return "";
}
       
    }
    
}
