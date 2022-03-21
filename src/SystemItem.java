import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.Objects;

/**
 * Abstract class representing an item with name and boolean writable.
 *
 * @invar name is a valid name.
 *       | IsValidName(name)
 *
 * @author Matias Daneels
 * @version 1.0
 */
public abstract class SystemItem {

    /**
     * String containing the name of the item.
     */
    public String name = null;

    /**
     * Directory where the item is in.
     */
    public Directory directory;

    /**
     * Constructor making a new item and setting the name and directory of this item.
     *
     * @param name The name for the new item we make.
     *
     * @effect set name to the given name.
     *      | setName(name)
     * @effect set directory to the given directory.
     */
    @Raw
    public SystemItem(String name, Directory directory){
        setName(name);
        setDirectory(directory);
    }

    /**
     * Set the directory to the given directory.
     *
     * @param directory The directory we want to set our directory to.
     * @throws IllegalArgumentException
     *        The given directory is null.
     *        | directory == null
     */
    @Raw @Basic
    public void setDirectory(Directory directory){
        if (directory.isValidAddItem(this)) {
            directory.addItem(this);
            this.directory = directory;
        }
    }

    /**
     * Returns whether the directory is a valid directory for this item.
     */
    public boolean canHaveAsDirectory(Directory directory){
        if (directory == null){
            throw new IllegalArgumentException("Directory cannot be null");
        }
        return true;
    }

    // MOET ANDERS VOOR ROOT WANT DAAR KAN DIRECTORY WEL NULL ZIJN! IK ZOU  CANHAVEASDIRECTORY AANPASSEN IN ROOT
    public boolean hasProperDirectory() {
        return (canHaveAsDirectory(directory) && (directory.hasAsItem(this)));
    }

    public Directory getDirectory() {
        return directory;
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
     * Check whether the given name is a legal name for an item.
     *
     * @param  	name
     *			The name to be checked
     * @return	True if the given string is effective, not
     * 			empty and consisting only of letters, digits,
     * 			hyphens and underscores; false otherwise.
     * 			| result ==
     * 			|	(name != null) && name.matches("[a-zA-Z_0-9.-]+")
     */
    public static boolean isValidName(String name){
        return (name != null && name.matches("[a-zA-Z_0-9.-]+"));
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
    public boolean hasSameName(SystemItem item) throws IllegalArgumentException{
        if (item == null){
            throw new IllegalArgumentException();
        }
        return(Objects.equals(getName(), item.getName()));
    }

    @Raw
    public void move(Directory directory){
        if (!canHaveAsDirectory(directory)) {
            throw new IllegalArgumentException("Invalid directory!");
        }
        if (this instanceof RootDirectory) {
            throw new IllegalActionException(this);
        }
        System.out.println(this.getDirectory().getName());
        getDirectory().remove(this);
        setDirectory(directory);
        directory.addItem(this);
    }

    public RootDirectory getRoot(){
        if (this instanceof RootDirectory){
            return (RootDirectory) this;
        }
        return getDirectory().getRoot();
    }

    public boolean isDirectOrIndirectChildOf(Directory directory){
        return directory.hasAsItem(this);
    }

    public abstract  int getTotalDiskUsage();
}
