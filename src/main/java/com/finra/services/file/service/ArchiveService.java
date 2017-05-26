package com.finra.services.file.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finra.services.file.dao.IDocumentDao;

/**
 * A service to save, find and get documents from an archive. 
 */
@Service("archiveService")
public class ArchiveService implements IArchiveService, Serializable {

    private static final long serialVersionUID = 8119784722798361327L;
    
    @Autowired
    private IDocumentDao fileSystemDocumentDao;

    /**
     * Saves a document in the archive.
     */
    @Override
    public DocumentMetadata save(Document document) {
    	fileSystemDocumentDao.insert(document); 
        return document.getMetadata();
    }
    
    /**
     * Finds document in the archive
     */
    @Override
    public List<DocumentMetadata> findDocuments(String personName, Date date) {
        return fileSystemDocumentDao.findByPersonNameDate(personName, date);
    }
    
    /**
     * Returns the document file from the archive
     */
    @Override
    public byte[] getDocumentFile(String id) {
        Document document = fileSystemDocumentDao.load(id);
        if(document!=null) {
            return document.getFileData();
        } else {
            return null;
        }
    }

    /*public IDocumentDao getDocumentDao() {
        return DocumentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        DocumentDao = documentDao;
    }*/
}
