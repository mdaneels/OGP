import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * A JUnit test class for testing the public methods of the Directory class
 * @author Matias Daneels
 * @version 1.0
 */
public class DirectoryTest {

    RootDirectory rootDirectory;
    Directory testDirectory;
    File testFile;

    @Before
    public void setUpFixture(){
        rootDirectory = new RootDirectory("root", true);

        testDirectory = new Directory("testDirectory", rootDirectory, true);

        testFile = new File("testFile", 50, true, testDirectory);
    }

    @Test
    public void testDirectoryValidName(){
        //Test will fail
        //Still problems with isValidName method
        //Cannot override for some reason
        assertEquals("root", rootDirectory.getName());
        rootDirectory.setName("validName");
        assertEquals("validName", rootDirectory.getName());
        rootDirectory.setName(".invalidName.");
        assertEquals("new_root_directory", rootDirectory.getName());
    }

    @Test
    public void testDirectorySetWritable(){
        assertTrue(testDirectory.isWritable());
        testDirectory.setWritable(false);
        assertFalse(testDirectory.isWritable());
    }

    @Test
    public void testDirectoryDefaultItems(){
        Directory emptyTestDirectory = new Directory("emptyTestDirectory", rootDirectory, true);
        assertEquals(new ArrayList<>(), emptyTestDirectory.getItems());
    }

    @Test
    public void testDirectoryAddItem(){
        assertEquals(rootDirectory, testDirectory.getDirectory());
        assertEquals(testDirectory, testFile.getDirectory());

    }

    @Test
    public void testDirectoryMoveItem(){

    }
}
