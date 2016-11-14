package rdp.fileupload.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import rdp.fileupload.FileMetadata;

/**
 * Marker interface for the FileMetadata repo.
 * 
 * Uses the spring JpaRepository build in CRUD functionality.
 * 
 * @author rdpoetker
 *
 */
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

}
