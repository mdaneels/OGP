import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FileTest {

    private static File file1;
    private File file2;

    @Before
    public void setUp()
            throws InterruptedException {
        // throws InterruptedException is nodig voor de sleep methode
        file1 = new File("testfile");
        Thread.sleep(100);
        file2 = new File("practica", 5000, true);
    }

    @Test
    public void testSetName() {
        assertEquals(file1.getName(), "testfile");
        file1.setName("&");
        assertEquals(file1.getName(), "undefined");
        file1.setName("newname");
        assertEquals(file1.getName(), "newname");
        file1.setName("");
        assertEquals(file1.getName(), "undefined");
        file1.setName("name.with_signs-");
        assertEquals(file1.getName(), "name.with_signs-");
    }

    @Test
    public void testEnlarge_LegalCase() {
        file2.enlarge(300);
        assertEquals(5300, file2.getSize());
    }

    @Test
    public void testShorten_LegalCase() {
        file2.shorten(500);
        assertEquals(4500, file2.getSize());
    }

    // probleem bij deze test: indien er vertraging zit tussen het uitvoeren van de .shorten methode(.) en assertEquals
    @Test
    public void testChangeDate_LegalCase() {
        file2.shorten(300);
        Date dateNow = new Date();
        assertEquals(dateNow ,file2.getChangeDateTime());
        file2.enlarge(300);
        dateNow = new Date();
        assertEquals(dateNow ,file2.getChangeDateTime());
    }

    // probleem bij deze test: programma wacht effectief 100ms om een andere datum te verkrijgen
    // throws InterruptedException is nodig voor de sleep methode
    @Test
    public void testHasOverlappingUsePeriod_LegalCase()
            throws InterruptedException {
        file1.setName("newNewName");
        Thread.sleep(100);
        file2.setName("newName");
        Thread.sleep(100);
        file1.setName("newName");
        assertEquals(true, file1.hasOverLappingUsePeriod(file2));
        assertEquals(false,file2.hasOverLappingUsePeriod(file1));
    }

    @Test(expected = IllegalActionException.class)
    public void testAccessRightsSetName_InvalidRights() {
        file1.setWritable(false);
        file1.setName("newName");
    }

    @Test
    public void testAccessRightsSetName_LegalCase() {
        file1.setWritable(true);
        file1.setName("newName");
    }

    @Test(expected = IllegalActionException.class)
    public void testAccessRightsEnlarge_InvalidRights() {
        file1.setWritable(false);
        file1.enlarge(20);
    }

    @Test
    public void testAccessRightsEnlarge_LegalCase() {
        file1.setWritable(true);
        file1.enlarge(20);
    }

    @Test(expected = IllegalActionException.class)
    public void testAccessRightsShorten_InvalidRights() {
        file1.setWritable(false);
        file1.shorten(20);
    }

    @Test
    public void testAccessRightsShorten_LegalCase() {
        file1.setWritable(true);
        file1.shorten(20);
    }
}
