import java.util.Date;
import java.text.SimpleDateFormat;
import static java.lang.Integer.MAX_VALUE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File {
    private String name;
    private int size;
    private boolean writable;
    private Date creationDateTime;
    private Date changeDateTime;
    private String WEGDOEN;


    private final int maxSize = MAX_VALUE;

    public File(String name, int size, boolean writable) {
        //setName(name);
        //setSize(size);
        //setWritable(writable);
        //setMaxSize(maxSize);
        //initCreationDateTime();
    }

    public File(String name) {
        //setName("name");
        //setSize(0);
        //setWritable(true);
    }

    /**
     * Set the initial date when creating the file.
     */
    private void initCreationDateTime(){
        this.creationDateTime = new Date();
    }

    private void changeCreationDateTime(){
        this.changeDateTime = new Date();
    }

    public Date getCreationDateTime() {
        return this.creationDateTime;
    }

    public Date getChangeDateTime() {
        return this.changeDateTime;
    }

    public boolean hasOverLappingUsePeriod(File file) {
        Date firstDateTimeF1 = this.getCreationDateTime();
        Date firstDateTimeF2 = file.getCreationDateTime();
        Date changeDateTimeF1 = this.getChangeDateTime();
        Date changeDateTimeF2 = file.getChangeDateTime();

        return (firstDateTimeF1.before(firstDateTimeF2)) && changeDateTimeF1.after(changeDateTimeF2);

    }

    private boolean checkName(String name){
        Pattern p = Pattern.compile("([a-zA-Z0-9.-_]*)");
        Matcher m = p.matcher(name);
        boolean correctSymbols;
        boolean isMatched = m.matches();
        if (isMatched){
            correctSymbols = true;
        }
        else{
            correctSymbols = false;
        }
        return correctSymbols;
    }

    public void setName(String name){
        if (this.checkName(name) && (name.length() > 0)){
            this.name = name;
        }
        else{
            this.name = "Undefined";
        }
    }
}
