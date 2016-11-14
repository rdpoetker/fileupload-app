package rdp.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import rdp.fileupload.persistence.FileMetadataRepository;
import rdp.fileupload.storage.StorageService;

/**
 * Test fixture for FileUploadController.
 * 
 * @author russell
 *
 */
@RunWith(JMockit.class)
public class FileUploadControllerTest {

	@Mocked StorageService storageService;
	@Mocked FileMetadataRepository fileMetaRepo;
	@Mocked FileMetadata mockFileMeta;
	@Mocked Resource mockRescfile;

	@Test
	public void getFiles() throws IOException {
		
		final FileUploadController bean = new FileUploadController(storageService, fileMetaRepo);
		
		final List<FileMetadata> exp = new ArrayList<>();
		final Sort sortDate = new Sort(Sort.Direction.DESC, "created");
		
		new MockUp<FileUploadController>() {
	        @Mock Sort sortByCreatedDate() { return sortDate; }
		};
		
		new Expectations() {{
									
			fileMetaRepo.findAll( sortDate );; result = exp;
	    }};
	    
		final List<FileMetadata> result = bean.getFiles();
		
		Assert.assertSame(exp, result);
		
	}
	
	@Test
	public void handleFileUpload(@Mocked final MultipartFile mockFile) {
		
		final FileUploadController bean = new FileUploadController(storageService, fileMetaRepo);
		
		final Date cd = new Date();
		
		new MockUp<FileUploadController>() {
	        @Mock Date getCurrentDate() { return cd; }
		};
		
		new Expectations() {{
			
			mockFile.getOriginalFilename(); result = "testorigfilename";
			mockFileMeta = new FileMetadata("testtitle", "testdesc", cd, "testorigfilename");
			
			storageService.store(mockFile);
	    	fileMetaRepo.save(mockFileMeta);
	    }};
	    
		bean.handleFileUpload(mockFile, "testtitle", "testdesc");
		
	}
	
	@Test
	public void serveFile() {
		
		final FileUploadController bean = new FileUploadController(storageService, fileMetaRepo);
						
		new Expectations() {{
			
			storageService.loadAsResource("testfilename"); result = mockRescfile;
			
			mockRescfile.getFilename(); result = "testfilename";
	       
	    }};
	    
		final ResponseEntity<Resource> result = bean.serveFile("testfilename");
		
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals("attachment; filename=\"testfilename\"", result.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0));
	}
}
