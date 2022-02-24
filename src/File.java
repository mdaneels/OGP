import java.util.Date;
import java.text.SimpleDateFormat;
import static java.lang.Integer.MAX_VALUE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class File {

    /**
     * Variable containing the name of the file.
     */
    private String name;

    /**
     * Variable containing the size of the file.
     */
    private int size;

    /**
     * Variable containing whether the name of the file and the size of
     * the file can be changed.
     */
    private boolean writable;

    /**
     * Variable that contains the creation date time.
     */
    private Date creationDateTime;

    /**
     * Variable that contains the last time the file was changed.
     */
    private Date changeDateTime;

    /**
     * Variable containing the max size that this file can have.
     */
    private final int maxSize = MAX_VALUE;

    public File(String name, int size, boolean writable) {
        setName(name);
        //setSize(size);
        //setWritable(writable);
        //setMaxSize(maxSize);
        initCreationDateTime();
    }

    public File(String name) {
        setName(name);
        //setSize(0);
        //setWritable(true);
        initCreationDateTime();
    }

    /**
     * Set the writable variable when creating the file.
     * @param writable
     *        Whether you want the file to be able to change size and name.
     */
    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    /**
     * Get the name of the file.
     * @return name
     *         The name of the file.
     */
    public String getName() {
        return name;
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

    /**
     * Checks if the input name is a valid name that can be used.
     * @param name
     *        The name that needs to be checked.
     * @return True if and only if the input name only contains letters, capital or not,
     * numbers, . , - or _ and has at least one character.
     */
    private boolean checkName(String name){
        Pattern p = Pattern.compile("([a-zA-Z0-9.-_]*)");
        Matcher m = p.matcher(name);
        boolean correctSymbols = false;
        boolean isMatched = m.matches();
        if (isMatched){
            correctSymbols = true;
        }
        return correctSymbols && (name.length() > 0);
    }

    /**
     * Set the name to the given input name if the input name is valid.
     * @param name
     *        Name that we want to call or file
     * @post The file name only contains letters, capital or not, numbers,
     *  . , - or _ and contains at least one character. If the input name does not follow these rules, the file
     *  name will be "undefined".
     */
    public void setName(String name){
        if (this.checkName(name) && (name.length() > 0)){
            this.name = name;
        }
        else{
            this.name = "undefined";
        }
    }
}
