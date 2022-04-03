package filesystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for testing the public methods of SystemItem.
 * @author Arthur Cremelie
 */

public class SystemItemTest {
    File file;
    Link link;
    Directory rootDirectory;
    Directory directory1;
    Directory directory2;

    @Before
    public void setUpFixture() {
        rootDirectory = new Directory("root", true);

        directory1 = new Directory("directory1", rootDirectory, true);
        directory2 = new Directory("directory2", directory1, true);

        file = new File("file", directory2, Type.PDF);
        link = new Link("link", true, directory1, file);
    }

    @Test
    public void filePathTest() {
        String path = file.getAbsolutePath();
        assertEquals("/root/directory1/directory2/file.PDF", path);
    }

    @Test
    public void linkPathTest() {
        String path = link.getAbsolutePath();
        assertEquals("/root/directory1/link", path);
    }
}
