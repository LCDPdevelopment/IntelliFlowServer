package org.acme;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import org.acme.model.InputFileData;
import org.w3c.dom.Document;



@Path("/BPMNprocess")
public class BpmnAppsProcess {
        @Inject MongoClient mongoClient;

  private MongoCollection getCollection(){
        return mongoClient.getDatabase("intleflow").getCollection("forms");
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

            FileWriter myWriter = new FileWriter("src\\main\\resources\\persons.bpmn");
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
    @GET
	@Path("/read")
    public File readBPMN(String inputdata) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {


        //    dbf.setFeature(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, true);
            
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src\\main\\resources\\persons.bpmn"));  
            File f = new File("src\\main\\resources\\persons.bpmn");
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