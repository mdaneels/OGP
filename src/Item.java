import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.Objects;

/**
 * Abstract class representing an item with name and boolean writable.
 *
 * @invar name is a valid name.
 *       | IsValidName(name)
 * @author Matias Daneels
 * @version 1.0
 */
public abstract class Item {

    /**
     * String containing the name of the item.
     */
    public String name = null;

    /**
     * Boolean containing whether the item can be changed, functionality may very for different subclasses.
     */
    public boolean isWritable;

    /**
     * Constructor making a new item and setting hte name and isWritable variable of this item.
     *
     * @param name The name for the new item we make.
     * @param writable Whether the item is changeable.
     *
     * @post name of the item is a valid name, rules may be different for subclasses.
     *      | IsValidName(name)
     * @post isWritable variable is set to writable parameter.
     *      | isWritable() == writable
     */
    @Raw
    public Item(String name, boolean writable){
        setName(name);
        setWritable(writable);
    }

    /**
     * Set the name of this item to the given name.
     *
     * @param   name
     * 			The new name for this item.
     * @post    If the given name is valid, the name of
     *          this item is set to the given name,
     *          otherwise the name of the item is set to a valid name (the default).
     *          | if (isValidName(name))
     *          |      then new.getName().equals(name)
     *          |      else new.getName().equals(getDefaultName())
     */
    @Raw @Model
    protected void setName(String name) {
        if (isValidName(name)) {
            this.name = name;
        } else {
            this.name = getDefaultName();
        }
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


    /**
     * Check whether the given name is a legal name for an item.
     *
     * @param  	name
     *			The name to be checked
     * @return	True if the given string is effective, not
     * 			empty and consisting only of letters, digits,
     * 			hyphens and underscores; false otherwise.
     * 			| result ==
     * 			|	(name != null) && name.matches("[a-zA-Z_0-9-]+")
     */
    protected static boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9-]+"));
    }

    /**
     * Give the default name for the given item.
     *
     * @return defaultName
     * | result == defaultName
     */
    public abstract String getDefaultName();

    /**
     * Return the name of this item.
     * @note		See Coding Rule 19 for the Basic annotation.
     */
    @Raw @Basic
    public String getName() {
        return name;
    }

    /**
     * @param item
     *        The item we want to know if it has the same name as this.
     * @return
     *        Return true if and only if the name of the given item is the same as the name of this.
     *        | result == (item.getName() == this.getName())
     * @throws IllegalArgumentException
     *        If item is null throw an IllegalArgumentException
     *        | if (item == null)
     *        | throw IllegalArgumentException
     */
    public boolean hasSameName(Item item) throws IllegalArgumentException{
        if (item == null){
            throw new IllegalArgumentException();
        }
        return(Objects.equals(getName(), item.getName()));
    }
}