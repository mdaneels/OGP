import java.util.Date;
import java.text.SimpleDateFormat;
import static java.lang.Integer.MAX_VALUE;

public class File {
    private String name;
    private int size;
    private boolean writable;
    private Date creationDateTime;
    private Date changeDateTime;


    private final int maxSize = MAX_VALUE;

    public File(String name, int size, boolean writable) {
        //setName(name);
        //setSize(size);
        //setWritable(writable);
        //setMaxSize(maxSize);
        //initCreationDateTime();
    }

    private void initCreationDateTime(){
        this.creationDateTime = new Date();
    }

    private void changeCreationDateTime(){
        this.changeDateTime = new Date();
    }

}
