package rdp.fileupload.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Config props for StorageService.
 * 
 * @author rdpoetker
 *
 */
@ConfigurationProperties("storage")
public class StorageProperties {

	/**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
}
