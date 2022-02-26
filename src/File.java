import java.util.Date;
import java.text.SimpleDateFormat;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.max;

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
    private int size = 0;

    /**
     * Variable containing whether the name of the file and the size of
     * the file can be changed.
     */
    private boolean writable = true;

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

    /**
     * Constructor to create a File object with a name, size and write permissions.
     * @param name The name of the file.
     * @param size The size of the file.
     * @param writable Whether you want the file to be writable (permissions).
     */
    public File(String name, int size, boolean writable) {
        setName(name);
        setSize(size);
        //setWritable(writable);
        //setMaxSize(maxSize);
        initCreationDateTime();
    }

    /**
     * Constructor to create a File object with a name. The size will be set to 0.
     * @param name The name of the file.
     */
    public File(String name) {
        setName(name);
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
     * Get the writable boolean of the file
     * @return writable
     *         Whether the file can change name and file size.
     */

    public boolean isWritable() {
        return writable;
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
     * Sets the size of the file to the given size in bytes
     * @pre the given size must be valid
     *      | isValidSize(size)
     * @param size
     *        the new size for the file
     */
    public void setSize(int size) {
        assert canHaveAsSize(size):
                "Class Invariant: valid size";
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    /**
     * Return a boolean reflecting whether the given size is valid
     * @param size
     *        the size needed to be checked
     * @return true if the given size is not negative and is not larger than the maximum size
     *         |result == ((size >= 0) && (size <= getMaxSize()))
     */
    public boolean canHaveAsSize(int size) {
        return (size >= 0) && (size <= maxSize);
    }

    /**
     * Enlages the size of the file with the given amount.
     *
     * @pre the amount must be valid and accepted for enlargment
     *      |canAcceptForEnlarge(amount)
     * @param amount
     *        the amount of bytes needed to be add to the size
     * @effect The new size of this file is equal to the old size incremented with the given amount of bytes.
     *         | new.getSize() = this.getSize() + amount
     */
    public void enlarge(int amount) {
        assert canAcceptForEnlarge(amount) :
                "Precondition: Acceptable amount for enlarge";
        setSize(size + amount);
        this.changeChangeDateTime(); // Change the date of last edit
    }

    /**
     * Returns a boolean reflecting whether the given amount is accepted to enlarge the size of the file
     * @param amount
     *        amount of bytes needed to be checked
     * @return true if the given amount is not negative, the file is writable and ...
     */
    public boolean canAcceptForEnlarge(int amount) {
        return ((amount > 0) && (isWritable())); // && canHaveAsSize(size + amount));
    }

    /**
     * Shortens the size of the file with the given amount.
     *
     * @pre the amount must be valid and accepted for shortening
     *      |canAcceptForShorten(amount)
     * @param amount
     *        the amount of bytes to reduce the size
     * @effect The new size of this file is equal to the old size reduced with the given amount of bytes.
     *         | new.getSize() = this.getSize() - amount
     */
    public void shorten(int amount) {
        assert canAcceptForShorten(amount) :
                "Precondition: Acceptable amount for shorten";
        setSize(size - amount);
    }

    /**
     * Returns a boolean reflecting whether the given amount is accepted to shorten the size of the file
     * @param amount
     *        amount of bytes needed to be checked
     * @return true if the given amount is not negative, the file is writable and ...
     */
    public boolean canAcceptForShorten(int amount) {
        return ((amount > 0) && (isWritable())); // && isValidSize(size - amount));
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
        Pattern p = Pattern.compile("([a-zA-Z0-9._-]*)");
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
