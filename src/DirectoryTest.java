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
    Directory testDirectory1;
    File testFile1;

    @Before
    public void setUpFixture(){
        rootDirectory = new RootDirectory("root", true);

        testDirectory1 = new Directory("testDirectory1", rootDirectory, true);

        testFile1 = new File("testFile1", 50, true, testDirectory1, Type.JAVA);
    }

    @Test
    public void testRootDirectoryValidName(){
        assertTrue(rootDirectory.isValidName("validName123_-"));
        assertFalse(rootDirectory.isValidName(".invalidName123."));
    }

    @Test
    public void testDirectoryValidName(){
        assertTrue(testDirectory1.isValidName("validName123_-"));
        assertFalse(testDirectory1.isValidName(".invalidName123."));
    }


    @Test
    public void testDirectoryValidNameConstructor(){
        Directory testDirectory2 = new Directory(".invalidName.", rootDirectory, true);
        assertEquals("new_directory", testDirectory2.getName());
        Directory testDirectory3 = new Directory("validName123_-", rootDirectory, true);
        assertEquals("validName123_-", testDirectory3.getName());
    }


    @Test
    public void testRootDirectoryValidNameConstructor(){
        RootDirectory testRootDirectory2 = new RootDirectory(".invalidName.", true);
        assertEquals("new_root_directory", testRootDirectory2.getName());
        RootDirectory testRootDirectory3 = new RootDirectory("validName123_-", true);
        assertEquals("validName123_-", testRootDirectory3.getName());
    }

    @Test
    public void testDirectorySetWritable(){
        assertTrue(testDirectory1.isWritable());
        testDirectory1.setWritable(false);
        assertFalse(testDirectory1.isWritable());
    }

    @Test
    public void testDirectoryDefaultItems(){
        Directory emptyTestDirectory = new Directory("emptyTestDirectory", rootDirectory, true);
        assertEquals(new ArrayList<>(), emptyTestDirectory.getItems());
    }

    @Test
    public void testDirectoryMoveItem(){
        Directory testDirectory2 = new Directory("testDirectory2", rootDirectory, true);
        testFile1.move(testDirectory2);
        assertTrue(testDirectory2.getItems().contains(testFile1));
        assertFalse(testDirectory1.getItems().contains(testFile1));
    }

    @Test
    public void testDirectoryIsValidIndex(){
        // rootDirectory has 1 item, see @Before
        assertTrue(rootDirectory.isValidIndex(1));
        assertTrue(rootDirectory.isValidIndex(0));
        assertFalse(rootDirectory.isValidIndex(2));
        assertFalse(rootDirectory.isValidIndex(69));
    }
}
