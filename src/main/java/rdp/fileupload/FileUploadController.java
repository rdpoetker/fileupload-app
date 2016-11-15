package rdp.fileupload;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rdp.fileupload.persistence.FileMetadataRepository;
import rdp.fileupload.storage.StorageException;
import rdp.fileupload.storage.StorageFileNotFoundException;
import rdp.fileupload.storage.StorageService;

/**
 * Controller class that provides a REST interface for processing requests and responses
 * for File Upload functionality.
 * 
 * @author rdpoetker
 *
 */
@RestController
@RequestMapping("/files")
public class FileUploadController {

	private static Log LOGGER = LogFactory.getLog(FileUploadController.class);
	
	private final StorageService storageService;
	
	private final FileMetadataRepository fileMetaRepo;

    @Autowired
    public FileUploadController(StorageService storageService, FileMetadataRepository fileMetaRepo) {
        this.storageService = storageService;
        this.fileMetaRepo = fileMetaRepo;
    }

    /**
     * Return all files uploaded in the app.
     * 
     * @return list of FileMetadata objects.
     */
    @RequestMapping(method = RequestMethod.GET)
	public List<FileMetadata> getFiles() {

		return fileMetaRepo.findAll( sortByCreatedDate() );
	}
    
    /**
     * Handles a file upload request.  The file in the multipart file will
     * be saved in the storage service along with the meta data params.
     * 
     * @param file file to be stored
     * @param title title of file
     * @param desc description of file
     * 
     * @return the FileMetadata object created for request
     */
    @RequestMapping(method = RequestMethod.POST)
    public FileMetadata handleFileUpload(@RequestParam("file") MultipartFile file,
    								@RequestParam("fmTitle") String title,
    								@RequestParam("fmDesc") String desc) {

    	final FileMetadata meta = new FileMetadata(title, desc, getCurrentDate(), file.getOriginalFilename());
    	
    	/*
    	 * Store the file in the file system first to ensure the write is successful.  
    	 * Probably should put better transaction management around these two calls 
    	 * in real system, or maybe use a document based DB to store file and metadata.
    	 */
    	storageService.store(file);
		
    	/*
    	 * Save the entity object in the db with the meta data
    	 */
    	fileMetaRepo.save(meta);
    	
    	if(LOGGER.isDebugEnabled()) {
    		
    		LOGGER.debug("stored file: " + meta);
    	}
        
        return meta;
    }

    /**
     * Load the file in the file store of the name filename.
     * 
     * @param filename
     * @return return file resource
     */
    @RequestMapping(path="/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    	
        final Resource file = storageService.loadAsResource(filename);
        
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
    	
    	final FileMetadata meta = fileMetaRepo.findOne(new Long(id));
    	
    	if( meta != null ) {
    	
    		final boolean result = storageService.delete(meta.getFileName());
    		
    		if( !result ) {
    		
    			LOGGER.warn("False reading on delete file from storage, but found in DB: "+meta.getFileName());
    		}
    		
    		// We'll delete anyway and go with the logging above, but should be better...
    		fileMetaRepo.delete(meta);
    	} else {
    		
    		return ResponseEntity.notFound().build();
    	}
                
        return ResponseEntity
                .ok()
                .build();
    }
    
    protected Date getCurrentDate() {
    	return new Date();
    }
    
    protected Sort sortByCreatedDate() {
        return new Sort(Sort.Direction.DESC, "created");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorageExc(StorageException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in file storage: " + exc.getMessage());
    }
    
}
