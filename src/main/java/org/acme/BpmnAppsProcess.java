package org.acme;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.acme.model.InputFileData;
import org.w3c.dom.Document;


import io.vertx.codegen.doc.Tag.Link;



@Path("/BPMNprocess")
public class BpmnAppsProcess {
        @Inject MongoClient mongoClient;

  private MongoCollection getCollection(){
        return mongoClient.getDatabase("intleflow").getCollection("forms");
    }
    private MongoCollection getFormMetaCollection(){
        return mongoClient.getDatabase("IntelliFlow").getCollection("formMeta");
    }

    @GET
    @Path("/formElements/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFormMeta(@PathParam("name") String name) {
        MongoCursor<org.bson.Document> cursor = getFormMetaCollection().find(Filters.eq("app", name)).iterator();
        String formdata="";

        try {
            Gson gson = new Gson();
   System.out.println(cursor.hasNext());
                   while (cursor.hasNext()) {
                       
                    org.bson.Document document =  cursor.next();
                    System.out.println(document);
                    formdata = gson.toJson(document);

                }
        } catch (Exception e) {
            //TODO: handle exception
        } finally {
            cursor.close();
        }
        return formdata ;

    }

  	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
    public String createBPMN(String inputdata) {
        try {

            Gson gson = new Gson();

            InputFileData data = gson.fromJson(inputdata, InputFileData.class);
            System.out.println(data.getXml());

            FileWriter myWriter = new FileWriter("src\\main\\resources\\"+data.getFileName()+".bpmn");
      myWriter.write(data.getXml());
      myWriter.close();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
              
        return "" ;

    }
    
  	@POST
      @Path("/createDMN")
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.TEXT_PLAIN)
      public String createDMN(String inputdata) {
          try {
  
              Gson gson = new Gson();
  
              InputFileData data = gson.fromJson(inputdata, InputFileData.class);
              System.out.println(data.getXml());
  
              FileWriter myWriter = new FileWriter("src\\main\\resources\\"+data.getFileName()+".dmn");
        myWriter.write(data.getXml());
        myWriter.close();
           
          } catch (Exception e) {
              e.printStackTrace();
          }
                
          return "" ;
  
      }

    private static InputStream readXmlFileIntoInputStream(final String fileName) {
        return BpmnAppsProcess.class.getClassLoader().getResourceAsStream(fileName);
    }

    @POST
	@Path("/formElements")
    public String createAppFormElements(String inputdata) {
        try {
            Gson gson = new Gson();
            InputFileData data = gson.fromJson(inputdata, InputFileData.class);
            UpdateOptions options = new UpdateOptions();
            options.upsert(true);
        
         getFormMetaCollection().updateOne(Filters.eq("app",  data.getAppName()), Updates.set("formElements",  data.getForElements()),options);
        
        } catch (Exception e) {
            //TODO: handle exception
        }
    

        return "";
    }

    @GET
	@Path("/read/{name}")
    public File readBPMN(@PathParam("name") String name) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {


        //    dbf.setFeature(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, true);
            
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src\\main\\resources\\"+name+".bpmn"));  
            File f = new File("src\\main\\resources\\"+name+".bpmn");
if(f.exists() && !f.isDirectory()) { 
    // do something
    System.out.println("File exisit");

}
   
        System.out.println(doc);
        return f;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }


    }
    @GET
	@Path("/readDMN/{name}")
    public File readDMN(@PathParam("name") String name) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {


        //    dbf.setFeature(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, true);
            
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src\\main\\resources\\"+name+".dmn"));  
            File f = new File("src\\main\\resources\\"+name+".dmn");
if(f.exists() && !f.isDirectory()) { 
    // do something
    System.out.println("File exisit");

}
   
        System.out.println(doc);
        return f;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }


    }


}