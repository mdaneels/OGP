package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Link extends SystemItem {

    /**
     * The reference of the link
     */
    private final SystemItem reference;

    /**
     * The validity of the link
     */
    private boolean valid = true;

    /**
     * Initialize a new link with given name, writability, directory and reference.
     *
     * @param  	name
     *         	The name of the new link.
     * @param  	writable
     *         	The writability of the new link.
     * @param   directory
     *          The directory of the new link.
     * @param   reference
     *          The reference of the new link.
     * @effect  The name of the file is set to the given name.
     * 			If the given name is not valid, a default name is set.
     *          | setName(name)
     * @effect	The writability is set to the given flag
     * 			| setWritable(writable)
     * @effect  The directory is set to the given directory
     *          | setDirectory(directory)
     * @post    The reference is set to the given reference, this can't be changed later
     *          | this.reference == reference
     * @throws IllegalArgumentException
     *         if the reference is not a valid reference
     *         | (!isValidReference(reference))
     */
    @Raw
    public Link(String name, boolean writable, Directory directory, SystemItem reference) throws IllegalArgumentException {
        super(name, directory);
        if (!isValidReference(reference)) {
            throw new IllegalArgumentException("Invalid reference!");
        }
        this.reference = reference;
    }

    /**
     * Checks if the reference is a valid reference
     * @param reference
     *        the reference needed to be checked
     * @return true if link refers to a proper item. The link can refer to a file or a directory.
     *         | (reference instanceof File) || (reference instanceof Directory)
     */
    public boolean isValidReference(SystemItem reference){
        return (reference instanceof File) || (reference instanceof Directory);
    }

    /**
     * @return the reference of the link
     */
    @Basic
    public SystemItem getReference() {
        return reference;
    }

    /**
     * Returns the validity of the link
     * @return true if the link is valid
     */
    @Basic
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the validity of the link to the given boolean
     * @param valid
     *        the validity to be set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }


    /**
     * Return the default name for a link
     * @return the default name for a link.
     *         | "new_link"
     */
    @Override
    public String getDefaultName() {
        return "new_link";
    }


    /**
     * Returns the total disk usage of a link
     * @return The total disk usage of a link is zero
     *         | result == 0
     */
    @Override
    public int getTotalDiskUsage() {
        return 0;
    }
}
