import static org.junit.Assert.*;
import org.junit.*;

public class FileTest {

    private static File file;

    @Before
    public void setUp() {
        file = new File("testfile");
    }

    @Test
    public void testChangeName() {
        assertEquals(file.getName(), "testfile");
        file.setName("&");
        assertEquals(file.getName(), "undefined");
        file.setName("newname");
        assertEquals(file.getName(), "newname");
        file.setName("");
        assertEquals(file.getName(), "undefined");
    }
}
