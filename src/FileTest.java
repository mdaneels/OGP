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
    public void setUp() throws InterruptedException{
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

    @Test
    public void testChangeDate() {
        file2.shorten(300);
        Date dateNow = new Date();
        assertEquals(dateNow ,file2.getChangeDateTime());
    }

    // probleem om dit te testen: programma wacht effectief een aantal ms om een andere datum te verkrijgen
    @Test
    public void testHasOverlappingUsePeriod()
        throws InterruptedException {
        file1.setName("newNewName");
        Thread.sleep(100);
        file2.setName("newName");
        Thread.sleep(100);
        file1.setName("newName");
        assertEquals(true, file1.hasOverLappingUsePeriod(file2));
        assertEquals(false,file2.hasOverLappingUsePeriod(file1));
    }
}
