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
        assertEquals("undefined", file.getName());
        file.setName("newname");
        assertEquals("newname", file.getName());
        file.setName("");
        assertEquals("undefined", file.getName());
        file.setName("name.with_signs-");
        assertEquals("name.with_signs-", file.getName());
    }
}
