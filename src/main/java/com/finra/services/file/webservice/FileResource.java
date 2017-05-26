package com.finra.services.file.webservice;

import com.finra.services.file.domain.Message;
//import com.finra.services.file.domain.MetaData;
import com.finra.services.file.service.ArchiveService;
import com.finra.services.file.service.Document;
import com.finra.services.file.service.DocumentMetadata;
import com.finra.services.file.service.MessageService;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.io.IOUtils;
//import com.sun.jersey.core.header.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.InputStream;
//import java.io.FileInputStream; 
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
@Path("/archive")
@Produces({MediaType.APPLICATION_JSON})
public class FileResource {

	//private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/Users/Jyothi/FileUploads/";

    @Autowired
    private MessageService messageService;
    @Autowired
    private ArchiveService archiveService;

    @GET
    @Path("/hello")
    public String hello() {
        return "Hello World";
    }

    @GET
    @Path("/messages")
    public List<Message> message() {
        return messageService.getMessages();
    }
    
    /**
     * Adds a document to the archive.
     * 
     * URI: file-services/archive/documents?person={person}&date={date} [POST]
     * 
     * @FormDataParam file A file posted in a multipart request
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return The meta data of the added document
     */
    @POST
    @Path("/documents")
    @Consumes({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFileWithMetaData(
    		//@FormDataParam("file") MultipartFile file,
    		@FormDataParam("file") InputStream fileData,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
            @QueryParam(value="person") String person,
            @QueryParam(value="date") String dateString
            //@QueryParam(value="date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date
            ) throws Exception {
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = formatter.parse(dateString);
    	
    	Document document = new Document(IOUtils.toByteArray(fileData), contentDispositionHeader.getFileName(), date, person );
        archiveService.save(document);
    	DocumentMetadata metaData = document.getMetadata();
    	return Response.status(200).entity(metaData).build();
    	
    	/*String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();
    	    // save the file to the server
    	    saveFile(file, filePath);
    	String output = "File saved to server location : " + filePath;
    	return Response.status(200).entity(output).build();*/

    }
    
    /**
     * Finds document in the archive. Returns a list of document meta data 
     * which does not include the file data. Use getDocument to get the file.
     * Returns an empty list if no document was found.
     * 
     * URI: /archive/documents?person={person}&date={date} [GET]
     * 
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return A list of document meta data
     */
    @GET
    @Path("/documents")
    public Response findDocument(
    		@QueryParam(value="person") String person,
            @QueryParam(value="date") String dateString) throws Exception{
        //HttpHeaders httpHeaders = new HttpHeaders();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = formatter.parse(dateString);
    	List<DocumentMetadata> metaData = archiveService.findDocuments(person,date);
    	return Response.status(200).entity(metaData).build();
        //return new ResponseEntity<List<DocumentMetadata>>(archiveService.findDocuments(person,date), httpHeaders,HttpStatus.OK);
    }
    
    /**
     * Returns the document file from the archive with the given UUID.
     * 
     * URI: /archive/document/{id} [GET]
     * 
     * @param id The UUID of a document
     * @return The document file
     */
    @GET
    @Path("/documents/{id}")
    public Response getDocument(@PathParam(value="id") String id) {         
        // send it back to the client
        //HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        byte[] content = archiveService.getDocumentFile(id);
        return Response.status(200).entity(content).build();
        //return new ResponseEntity<byte[]>(archiveService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }
    
    	// save uploaded file to a defined location on the server
        /*private void saveFile(InputStream uploadedInputStream,
                String serverLocation) {
            try {
                OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
                int read = 0;
                byte[] bytes = new byte[1024];
                outpuStream = new FileOutputStream(new File(serverLocation));
                while ((read = uploadedInputStream.read(bytes)) != -1) {
                    outpuStream.write(bytes, 0, read);
                }
                outpuStream.flush();
                outpuStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

}
