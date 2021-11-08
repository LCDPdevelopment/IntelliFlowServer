package org.acme;

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

import org.acme.model.InputFileData;
import org.bson.Document;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Path("/formdetails")
public class GreetingResource {
        @Inject MongoClient mongoClient;

  private MongoCollection getCollection(){
        return mongoClient.getDatabase("IntelliFlow").getCollection("forms");
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@PathParam("name") String name) {

         Gson gson = new Gson();
         String formdata="";

                MongoCursor<Document> cursor = getCollection().find(Filters.eq("name", name)).iterator();
                        List<FormsService> list = new ArrayList<>();
   try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                FormsService forms = new FormsService();
                forms.setName(document.getString("name"));
                forms.setDescription(document.getString("formData"));
                formdata=document.getString("formData");
                list.add(forms);
            }
        } finally {
            cursor.close();
        }
         String jsonCartList = gson.toJson(list);

        return formdata ;

    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String AddDocument(String inputdata) {
        System.out.println(inputdata);
        Gson gson = new Gson();
try {
    InputFileData data = gson.fromJson(inputdata, InputFileData.class);
    UpdateOptions options = new UpdateOptions();
    options.upsert(true);

 getCollection().updateOne(Filters.eq("name",  data.getFileName()), Updates.set("formData",  data.getXml()),options);

 return"";

} catch (Exception e) {
    return "";
}
       
    }



}