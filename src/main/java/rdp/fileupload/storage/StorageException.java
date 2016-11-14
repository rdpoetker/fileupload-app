package rdp.fileupload.storage;

/**
 * Exception for runtime exceptions occurring in storage service.
 * 
 * @author rdp
 *
 */
public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = -224596711446498966L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
