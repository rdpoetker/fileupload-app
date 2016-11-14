package rdp.fileupload;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity object representing file metadata.
 * 
 * @author rdpoetker
 *
 */
@Entity
public class FileMetadata {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String description;

	private Date created;
	
	private String fileName;
	
	protected FileMetadata() {}
			
	public FileMetadata(String title, String description, Date created,
			String fileName) {
		super();
		this.title = title;
		this.description = description;
		this.created = created;
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FileMetadata [id=" + id + ", title=" + title + ", description="
				+ description + ", created=" + created + ", fileName="
				+ fileName + "]";
	}
	
	
}
