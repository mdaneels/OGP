import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.max;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void setSize(int size) {
        assert isValidSize(size):
                "Class Invariant: valid size";
        this.size = size;
    }

    public boolean isValidSize(int size) {
        return (size >= 0) && (size <= maxSize);
    }

    /**
     * Enlage the size of the file with the given amount.
     *
     * @pre the amount must be larger than zero.
     *
     * @param amount
     *        the amount of bytes to add to the size.
     *
     * @effect The new size of this file is equal to the old size incremented with the given amount of bytes.
     * | new.getSize() = this.getSize() + amount
     */
    public void enlarge(int amount) {
        assert canAcceptForEnlarge(amount) :
                "Precondition: Acceptable amount for enlarge";
        setSize(size + amount);
        }
    }

    public boolean canAcceptForEnlarge(int amount) {
        return ((amount > 0) && (isWritable()) && isValidSize(size + amount));
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
