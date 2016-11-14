package rdp.fileupload.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for storing and retrieving files.
 * 
 * @author rdpoetker
 *
 */
public interface StorageService {

	/**
	 * Initialize the storage service so that resources can be persisted.
	 */
	void init();

	/**
	 * Store the MultipartFile file in the store.  Only the file stream within the MultipartFile object is saved.
	 * 
	 * @param file the MultipartFile to store.
	 */
    void store(MultipartFile file);

    /**
     * Load and return the Resource file of the filename passed as an param.
     * 
     * @param filename
     * @return
     */
    Resource loadAsResource(String filename);

    /**
     * Delete all files saved in the store.
     */
    void deleteAll();
    
}
