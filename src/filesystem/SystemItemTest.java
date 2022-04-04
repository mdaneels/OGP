package filesystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemItemTest {

    Directory rootDirectory1;
    Directory rootDirectory2;
    Directory directory1;
    Directory directory2;
    File file1;
    File file2;
    File file3;
    Link link;

    @Before
    public void setUpFixture(){
        rootDirectory1 = new RootDirectory("root", true);
        rootDirectory2 = new RootDirectory("root", true);
        directory1 = new Directory("map", rootDirectory1, true);
        directory2 = new Directory("map", directory1, true);
        file1 = new File("bestand",100, true, directory1, Type.JAVA);
        file2 = new File("bestand",50, true, rootDirectory1, Type.JAVA);
        file3 = new File("bestand",50, true, rootDirectory2, Type.JAVA);
        link = new Link("link", true, rootDirectory1, file1);
    }

    @Test
    public void testTotalDiskUsage(){
        assertEquals(rootDirectory1.getTotalDiskUsage(), 150);
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
}
