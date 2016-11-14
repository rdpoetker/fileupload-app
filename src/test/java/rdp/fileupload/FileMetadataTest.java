package rdp.fileupload;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test fixture for FileMetadata.
 * 
 * @author rdpoetker
 *
 */
public class FileMetadataTest {

	@Test
	public void creationAndUse() {
		
		final FileMetadata fm = new FileMetadata();
		
		Assert.assertNull(fm.getId());
		Assert.assertNull(fm.getDescription());
		Assert.assertNull(fm.getFileName());
		Assert.assertNull(fm.getTitle());
		Assert.assertNull(fm.getCreated());
		
	}
	
	@Test
	public void getAndSet() {
		
		final FileMetadata fm = new FileMetadata();
						
		fm.setId(new Long(123));
		fm.setDescription("testdesc");
		fm.setFileName("testfilename");
		fm.setTitle("testtitle");
		
		final Date td = new Date();
		fm.setCreated(td);
		
		Assert.assertEquals(new Long(123), fm.getId());
		Assert.assertEquals("testdesc", fm.getDescription());
		Assert.assertEquals("testfilename", fm.getFileName());
		Assert.assertEquals("testtitle", fm.getTitle());
		Assert.assertSame(td, fm.getCreated());
	}
}
