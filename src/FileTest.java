import static org.junit.Assert.*;
import org.junit.*;

public class FileTest {

    private static File file;

    @Before
    public void setUp() {
        file = new File("testfile");
    }

    @Test
    public void testSetName() {
        assertEquals(file.getName(), "testfile");
        file.setName("&");
        assertEquals(file.getName(), "undefined");
        file.setName("newname");
        assertEquals(file.getName(), "newname");
        file.setName("");
        assertEquals(file.getName(), "undefined");
        file.setName("name.with_signs-");
        assertEquals(file.getName(), "name.with_signs-");
    }
}
