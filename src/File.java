import java.util.Date;

import static java.lang.Integer.MAX_VALUE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class of files involving a name, size, the date of creation, the date of the last editing moment and a facility concering the write permission
 * @version 1.0
 * @author Arthur Cremelie, Matias Daneels, Eva Haanen
 */
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
    private static final long maxSize = MAX_VALUE;

    /**
     * Initializes a file with the given name, size and write permissions.
     * @param name The name of the file.
     * @param size The size of the file.
     * @param writable Whether you want the file to be writable (permissions).
     * @post if the given size is not negative and smaller than the maximum size,
     * the initial size of this file is set to the given size
     */
    public File(String name, int size, boolean writable) {
        setName(name);
        setSize(size);
        setWritable(writable);
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
     * Sets the writable variable when creating the file.
     * @param writable
     *        True if it's possible to change size and name of the file.
     */
    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    /**
     * Get the writable boolean of the file
     * @return writable
     *        True if it's possible to change size and name of the file
     */

    public boolean isWritable() {
        return writable;
    }

    /**
     * Get the name of the file.
     * @return name
     *         The name of the file.
     */
    //@Basic
    public String getName() {
        return name;
    }

    /**
     * Sets the size of the file to the given size in bytes
     * @pre the given size must be valid
     *      | isValidSize(size)
     * @param size
     *        the new size for the file
     * @post the size of the file is set to the given size
     */
    public void setSize(int size) {
        assert canHaveAsSize(size):
                "Class Invariant: valid size";
        this.size = size;
    }

    /**
     * Returns the size of the file.
     * @return name
     *         The size of the file.
     */
    //@Basic
    public int getSize() {
        return size;
    }


    public static long getMaxSize() {
        return maxSize;
    }

    /**
     * Returns a boolean reflecting whether the given size is valid
     * @param size
     *        the size needed to be checked
     * @return true if the given size is not negative and is not larger than the maximum size
     *         |result == ((size >= 0) && (size <= getMaxSize()))
     */

    public boolean canHaveAsSize(int size) {
        return (size >= 0) && (size <= maxSize);
    }

    /**
     * Enlagres the size of the file with the given amount.
     *
     * @pre the amount must be valid and accepted for enlargement
     *      |canAcceptForEnlarge(amount)
     * @param amount
     *        the amount of bytes needed to be added to the size
     * @post The new size of this file is equal to the old size incremented with the given amount of bytes.
     *         | new.getSize() = this.getSize() + amount
     * @throws ...
     */
    public void enlarge(int amount)
    throws IllegalActionException {
        if (!isWritable()) {
            throw new IllegalActionException(writable, this);
        }
        assert canAcceptForEnlarge(amount) :
                "Precondition: Acceptable amount for enlarge";
        int oldSize = this.size;
        setSize(size + amount);
        if (size == oldSize + amount) {
            this.changeChangeDateTime(); // Change the date of last edit
        }
    }

    /**
     * Returns a boolean reflecting whether the given amount is accepted to enlarge the size of the file
     * @param amount
     *        amount of bytes needed to be checked
     * @return true if the given amount is not negative
     */
    public boolean canAcceptForEnlarge(int amount) {
        return (amount > 0); // && canHaveAsSize(size + amount));
    }

    /**
     * Shortens the size of the file with the given amount.
     *
     * @pre the amount must be valid
     *      |canAcceptForShorten(amount)
     * @param amount
     *        the amount of bytes to reduce the size
     * @post The new size of this file is equal to the old size reduced with the given amount of bytes.
     *         | new.getSize() = this.getSize() - amount
     */
    public void shorten(int amount)
    throws IllegalActionException {
        if (!isWritable()) {
            throw new IllegalActionException(writable, this);
        }
        assert canAcceptForShorten(amount) :
                "Precondition: Acceptable amount for shorten";
        int oldSize = this.size;
        setSize(size - amount);
        if (size == oldSize - amount) {
            this.changeChangeDateTime(); // Change the date of last edit
        }
    }

    /**
     * Returns a boolean reflecting whether the given amount is accepted to shorten the size of the file
     * @param amount
     *        amount of bytes needed to be checked
     * @return true if the given amount is not negative, the file is writable and ...
     */
    public boolean canAcceptForShorten(int amount) {
        return (amount > 0); //&& (isWritable())); // && isValidSize(size - amount));
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
     * @param file
     *        The other file.
     * @return True if and only if the file has an overlapping period of changing the file.
     */
    public boolean hasOverLappingUsePeriod(File file)
    throws IllegalFileException {
        // probleem: dit moet niet defensief geprogrammeerd worden, wel totaal
        if (!isValidFile(file) || !hasBeenChanged(file)) {
            throw new IllegalFileException(file);
        }
        Date firstDateTimeF1 = this.getCreationDateTime();
        Date firstDateTimeF2 = file.getCreationDateTime();
        Date changeDateTimeF1 = this.getChangeDateTime();
        Date changeDateTimeF2 = file.getChangeDateTime();

        return (firstDateTimeF1.before(firstDateTimeF2)) && changeDateTimeF1.after(changeDateTimeF2);

    }

    public boolean isValidFile(File file){
        return (file != null) && (file != this) && (file.getChangeDateTime() != null);
    }

    public boolean hasBeenChanged(File file) {
        return file.getChangeDateTime() != null;
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
    public void setName(String name)
    throws IllegalActionException {
        if (!isWritable()) {
            throw new IllegalActionException(writable, this);
        }

        if (this.checkName(name) && (name.length() > 0)){
            this.name = name;
        }
        else{
            this.name = "undefined";
        }
    }
}
