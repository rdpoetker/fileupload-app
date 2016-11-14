package rdp.fileupload.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileSystemStorageServiceTest {
		
	@Test
	public void creation() {
		
		final StorageProperties props = new StorageProperties();
		
		final FileSystemStorageService service = new FileSystemStorageService(props);
		
		Assert.assertNotNull(Deencapsulation.getField(service, "rootLocation"));
	}
	
	@Test
	public void store(@Mocked final MultipartFile mockMPFile, @Mocked InputStream mockIS) throws IOException {
		
		final StorageProperties props = new StorageProperties();
		
		final FileSystemStorageService service = new FileSystemStorageService(props);
		
		new MockUp<Files>() {
	        @Mock long copy(InputStream in, Path target, CopyOption... options) throws IOException { 
	        
	        	Assert.assertSame(mockIS, in);
	        	Assert.assertEquals("upload-dir/testfilename", target.toString());	        	
	        	return 1; 
	        }
		};
				
		new Expectations() {{
			
			mockMPFile.isEmpty(); result = false;
			
			mockMPFile.getInputStream(); result = mockIS;
			
			mockMPFile.getOriginalFilename(); result = "testfilename";
			
	    }};
		
		service.store(mockMPFile);
	}
	
	@Mocked Resource mockResource;
		
	@Test
	public void loadAsResource() throws IOException, URISyntaxException {
		
		final StorageProperties props = new StorageProperties();
		
		final FileSystemStorageService service = new FileSystemStorageService(props);
		
		new MockUp<FileSystemStorageService>() {
	        @Mock Resource getResource(final Path file) { 
	        	return mockResource;
	        }
		};
				
		new Expectations() {{
			            
			mockResource.exists(); result = true;
									
	    }};
		
	    // exists is true
		Resource result = service.loadAsResource("testfilename");
		
		Assert.assertSame(mockResource, result);
		
		new Expectations() {{
            
			mockResource.exists(); result = false;
			mockResource.isReadable(); result = true;
									
	    }};
		
	    // exists is false, but readable is true
	    result = service.loadAsResource("testfilename");
	    
	    Assert.assertSame(mockResource, result);
	   	   
	}
	
	@Test(expected = StorageFileNotFoundException.class)
	public void loadAsResourceNotFoundError() throws IOException {
		
		final StorageProperties props = new StorageProperties();
		
		final FileSystemStorageService service = new FileSystemStorageService(props);
		
		new MockUp<FileSystemStorageService>() {
	        @Mock Resource getResource(final Path file) { 
	        	return mockResource;
	        }
		};
					    
	    new Expectations() {{
            
			mockResource.exists(); result = false;
			mockResource.isReadable(); result = false;
									
	    }};
		
	    service.loadAsResource("testfilename");
	}
}
