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

    /**
     * Set the date when changing a file.
     */
    private void changeChangeDateTime(){
        this.changeDateTime = new Date();
    }

    /**
     * Get the creationDateTime.
     * @return creationDateTime
     */
    public Date getCreationDateTime() {
        return this.creationDateTime;
    }

    /**
     * Get the changeDateTime.
     * @return changeDateTime
     */
    public Date getChangeDateTime() {
        return this.changeDateTime;
    }

    /**
     * Check if the file has an overlapping use period with another file.
     * @param file The other file.
     * @return True or false.
     */
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
