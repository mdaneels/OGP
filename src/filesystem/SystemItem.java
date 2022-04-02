package filesystem;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.Dictionary;
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
     * filesystem.Directory where the item is in.
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
     */
    @Raw @Basic

    public void setDirectory(Directory directory){
        if (! this.canHaveAsDirectory(directory)){
            throw new IllegalArgumentException();
        }
        this.directory = directory;

    }

    /**
     * Returns whether the directory is a valid directory for this item.
     */
    public boolean canHaveAsDirectory(Directory directory){
        if (directory == null){
            throw new IllegalArgumentException("filesystem.Directory cannot be null");
        }
        return true;
    }

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
    public boolean isValidName(String name){
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

    /**
     * Moves this item to the given directory
     * @param directory
     *        the directory the file needs te be placed in
     * @throws IllegalArgumentException
     *         The given directory must be a valid directry
     *         | !canHaveAsDirectory(directory)
     * @throws IllegalActionException
     *          The item can't be a  RootDirectory
     *         | (this instanceof RootDirectory)
     * @effect The item is placed into the given directory.
     *          | setDirectory(directory) && directory.addItem(this)
     */
    public void move(Directory directory) throws IllegalArgumentException, IllegalActionException {
        if (!canHaveAsDirectory(directory)) {
            throw new IllegalArgumentException("Invalid directory!");
        }
        if (this instanceof RootDirectory) {
            throw new IllegalActionException(this);
        }
        getDirectory().remove(this);
        setDirectory(directory);
        directory.addItem(this);
    }

    /**
     * The root directory of the item is given
     * @return this if the item is a root directory
     *         if this isn't a root directory, the root directory of the item's directory is given.
     *          | if (getDirectory() == null)
     *          |   result == this
     *          | else
     *          |   result == getDirectory().getRoot()
     */
    public SystemItem getRoot(){
        if (directory == null){
            return this;
        }
        Directory parent = directory;
        return parent.getRoot();
    }

    /**
     * Returns if a given directory is an indirect or direct parent directory of the item
     * @param directory
     *        the directory needed to be checked if it is a parent directory of the item
     * @return true if the directory of the item is equal to the given directory
     *         (the item is a direct child of the given directory)
     *         true if the directory of the item is a direct or indirect child of the given directory
     *         (the item is an indirect child of the given directory)
     *         false if the item is not related to the given directory
     *         | if (getDirectory() == directory)
     *         |    result == true
     *         | if (!(getDirectory() == directory))
     *         |    result == getDirectory().isDirectOrIndirectChildOf(directory)
     *         | else
     *         |    result == false
     *
     */
    public boolean isDirectOrIndirectChildOf(Directory directory){
        Directory parent = this.directory;

        if (parent == directory){
            return true;
        }
        if (!(parent.getDirectory() == null)){
            return parent.isDirectOrIndirectChildOf(directory);
        }
        return false;

    }

    /**
     * Returns the absolute path of an item as a string.
     *
     * @return String of the absolute path of the item.
     */
    public String getAbsolutePath() {
        String path = "/";
        Directory parent = this.getDirectory();
        while (parent != null) {
            path += parent.getName();
            path += "/";
            parent = parent.getDirectory();
        }
        path += this.getName();
        if (this instanceof File) {
            path += ".";
            path += ((File) this).getExtension();
        }
        return path;
    }

    /**
     * Returns the total disk usage of the directory.
     */
    public abstract  int getTotalDiskUsage();
}
