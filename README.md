# FileServices
This application is a REST API implemented using Jersey and Spring Boot.
- API to upload a file with a few meta-data fields. Persist meta-data in persistence store
- API to get file meta-data based on query params in the request
- API to get file meta-data for a specific file ID

Future Enhancements::
We can write a scheduler in the same app to poll for new items in the last hour and send an email.

===========API USAGE==================
(i) Adds a document to the archive.
POST:
URI: file-services/archive/documents?person={person}&date={date} [POST]
@FormDataParam file A file posted in a multipart request
@QueryParam person The name of the uploading person
@QueryParam date The date of the document
@return The meta data of the added document

(ii) Finds document in the archive. Returns a list of document meta data 
     which does not include the file data. Use getDocument to get the file.
     Returns an empty list if no document was found.
GET:
URI: /archive/documents?person={person}&date={date} [GET]
@QueryParam person The name of the uploading person
@QueryParam date The date of the document
@return A list of document meta data

(iii) Returns the document file from the archive with the given UUID (File Id).
GET:
URI: /archive/document/{id} [GET]
@PathParam id The UUID of a document
@return The document file
