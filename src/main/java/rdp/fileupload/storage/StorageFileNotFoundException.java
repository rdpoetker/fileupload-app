package rdp.fileupload.storage;

/**
 * Exception for file not found in the storage service.
 * 
 * @author rdpoetker
 *
 */
public class StorageFileNotFoundException extends StorageException {
	
	private static final long serialVersionUID = -4034884574525684761L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
