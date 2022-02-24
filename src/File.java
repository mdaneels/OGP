import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.max;

public class File {
    private String name;
    private int size;
    private boolean writable;
    private int maxSize = MAX_VALUE;

    public File(String name, int size, boolean writable) {
        //setName(name);
        //setSize(size);
        //setWritable(writable);
        //setMaxSize(maxSize);
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
}
