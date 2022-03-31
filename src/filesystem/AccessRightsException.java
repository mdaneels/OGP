package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class AccessRightsException extends RuntimeException{
    /**
     * Required because this class inherits from Exception
     */
    private static final long serialVersionUID = 1L;

    /**
     * Variable referencing the file to which change was denied.
     */
    private final SystemItem item;

    /**
     * Initialize this new file not writable exception involving the
     * given file.
     *
     * @param	item
     * 			The item for the new item not writable exception.
     * @post	The item involved in the new item not writable exception
     * 			is set to the given item.
     * 			| new.getItem() == item
     */
    public AccessRightsException(SystemItem item) {
        this.item = item;
    }

    /**
     * Return the file involved in this file not writable exception.
     */
    @Basic
    @Immutable
    public SystemItem getItem() {
        return item;
    }


}
