package rdp.fileupload.storage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test fixture for StorageProperties.
 * 
 * @author rdpoetker
 *
 */
public class StoragePropertiesTest {

	@Test
	public void creation() {
		
		final StorageProperties sp = new StorageProperties();
		
		Assert.assertEquals("upload-dir", sp.getLocation());
	}
	
	@Test
	public void change() {
		
		final StorageProperties sp = new StorageProperties();
		
		Assert.assertEquals("upload-dir", sp.getLocation());
		
		sp.setLocation("diff-loc");
		
		Assert.assertEquals("diff-loc", sp.getLocation());
	}
}
