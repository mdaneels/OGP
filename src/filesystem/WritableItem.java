package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class for writable items.
 *
 * @author Matias Daneels
 * @author Eva Haanen
 * @author Arthur Cremelie
 */

public abstract class WritableItem extends SystemItem{

    /**
     * Boolean containing whether the item can be changed, functionality may very for different subclasses.
     */
    public boolean isWritable;

    /**
     * Constructor making a new item and setting the name, isWritable variable and directory of this item.
     *
     * @param name The name for the new item we make.
     * @param writable Whether the item is changeable.
     *
     * @effect isWritable variable is set to writable parameter.
     *      | setWritable(writable)
     * @effect set name to the given name.
     *      | setName(name)
     */
    @Raw
    public WritableItem(String name, Directory directory, boolean writable){
        super(name, directory);
        setWritable(writable);
    }

    /**
     * Set the writability of this item to the given writability.
     *
     * @param isWritable
     *        The new writability
     * @post  The given writability is registered as the new writability
     *        for this item.
     *        | new.isWritable() == isWritable
     */
    @Raw
    protected void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }

    /**
     * Check whether this file is writable.
     */
    @Raw @Basic
    protected boolean isWritable() {
        return isWritable;
    }

    @Override
    public void move(Directory directory) {
        if (!isWritable){
            throw new AccessRightsException(this);
        }
        super.move(directory);
    }
}


