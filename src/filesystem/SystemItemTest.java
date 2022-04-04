package filesystem;

import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit testclass for testing public methods of the class SystemItem
 *
 * @author Arthur Cremelie
 * @author Eva Haanen
 */

import static org.junit.Assert.*;

public class SystemItemTest {

    Directory rootDirectory1;
    Directory rootDirectory2;
    Directory directory1;
    Directory directory2;
    File file1;
    File file2;
    File file3;
    File filePDF;
    File fileJAVA;
    File fileTXT;
    Link link;

    @Before
    public void setUpFixture(){
        rootDirectory1 = new RootDirectory("root1", true);
        rootDirectory2 = new RootDirectory("root2", true);
        directory1 = new Directory("directory1", rootDirectory1, true);
        directory2 = new Directory("directory2", directory1, true);
        file1 = new File("bestand",100, true, directory1, Type.JAVA);
        file2 = new File("bestand",50, true, rootDirectory1, Type.JAVA);
        file3 = new File("bestand",50, true, rootDirectory2, Type.JAVA);
        filePDF = new File("file", directory2, Type.PDF);
        fileJAVA = new File("file", directory2, Type.JAVA);
        fileTXT = new File("file", directory2, Type.TXT);
        link = new Link("link", true, directory1, file1);
    }

    @Test
    public void testTotalDiskUsage(){
        assertEquals(rootDirectory1.getTotalDiskUsage(), 150);
        assertEquals(rootDirectory2.getTotalDiskUsage(), 50);

    }

    @Test
    public void testIsDirectOrIndirectChildOf(){
        assertFalse(directory1.isDirectOrIndirectChildOf(rootDirectory2));
        assertFalse(rootDirectory2.isDirectOrIndirectChildOf(rootDirectory1));
        assertTrue(file2.isDirectOrIndirectChildOf(rootDirectory1));
        assertTrue(file1.isDirectOrIndirectChildOf(rootDirectory1));
    }

    @Test
    public void testGetRoot(){
        assertEquals(rootDirectory1.getRoot(), rootDirectory1);
        assertEquals(link.getRoot(), rootDirectory1);
        assertEquals(file1.getRoot(), rootDirectory1);
        assertEquals(file2.getRoot(), rootDirectory1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testMoveIllegalCase(){
        file1.move(null);
        directory1.move(directory2);
    }

    @Test (expected = AccessRightsException.class)
    public void testMoveIllegalCase2(){
        rootDirectory1.setWritable(false);
        file1.move(rootDirectory1);
    }


    @Test
    public void testMoveLegalCase(){
        file1.move(rootDirectory1);
        assertFalse(directory1.hasAsItem(file1));
        assertEquals(file1.getDirectory(), rootDirectory1);
        assertTrue(rootDirectory1.hasAsItem(file1));
    }

    @Test
    public void filePathTest() {
        String pathPDF = filePDF.getAbsolutePath();
        assertEquals("/root1/directory1/directory2/file.PDF", pathPDF);
        String pathJAVA = fileJAVA.getAbsolutePath();
        assertEquals("/root1/directory1/directory2/file.JAVA", pathJAVA);
        String pathTXT = fileTXT.getAbsolutePath();
        assertEquals("/root1/directory1/directory2/file.TXT", pathTXT);
    }

    @Test
    public void linkPathTest() {
        String path = link.getAbsolutePath();
        assertEquals("/root1/directory1/link", path);
    }

    @Test
    public void directoryPathTest() {
        String path = directory2.getAbsolutePath();
        assertEquals("/root1/directory1/directory2", path);
    }
}
