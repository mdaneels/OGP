package filesystem;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal attempts to change a file.
 *
 *  @author Matias Daneels
 *  @author Eva Haanen
 *  @author Arthur Cremelie
 * @version	1.0
 */
public class NotInMapException extends RuntimeException {

    /**
     * Required because this class inherits from Exception
     */
    private static final long serialVersionUID = 1L;

    /**
     * Variable referencing the directory to which an item was not found.
     */
    private final Directory directory;

    /**
     * Variable referencing the item that was not found in the given directory.
     */
    private final String itemName;

    /**
     * Initialize this new not in map exception consisting of the directory and the item that was not found.
     *
     * @param    directory
     * 			The directory for the new not in map exception.
     * @post	The directory were the item was not found is now this.directory
     * @post    The item that was not found is now this.item.
     */
    public NotInMapException(Directory directory, String itemName) {
        this.directory = directory;
        this.itemName = itemName;
    }

    /**
     * Return the directory that does not have the asked item.
     * @return
     */
    @Basic @Immutable
    public Directory getDirectory() {
        return directory;
    }

    /**
     * Return the name of the item that was not found in the given directory.
     */
    @Basic @Immutable
    public String getItem(){
        return itemName;
    }

}