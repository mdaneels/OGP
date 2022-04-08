package filesystem;

import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * Class representing a directory, having a list of items.
 *
 *  @author Matias Daneels
 *  @author Eva Haanen
 *  @author Arthur Cremelie
 * @version 1.0
 */
public class Directory extends WritableItem{

    /**
     * List containing all the items that are in the directory.
     *
     * @invar All the elements in the list have this directory as directory
     *        | hasProperElements()
     */
    public List<SystemItem> items = new ArrayList<SystemItem>();

    /**
     * Method for making a directory given the name of the directory and whether the directory is writable.
     * @param name
     *        Wanted name for the new directory.
     * @param directory
     *        The directory of the item.
     * @param writable
     *        Boolean representing whether we want the directory to be able to change name, remove items and add items.
     */
    @Raw
    public Directory(String name, Directory directory, boolean writable){
        super(name, directory, writable);
    }

    /**
     * Check if the given directory is a valid directory for this directory. This will only be valid if the
     * directory is null (the directory is a root directory) or the directory that is given is not in the directory.
     * @param directory The directory we want to check if it is valid.
     * @return true if and only if the given directory is null or the given directory is not in the directory.
     *        | result == ((directory == null) || !(this.hasAsItem(directory))
     */
    @Override
    public boolean canHaveAsDirectory(Directory directory){
        return ((directory == null)) || !(this.hasAsItem(directory));
    }

    /**
     * Checks if all the elements in the list of items are proper elements
     * @return true if all the elements have this directory as their directory
     *          | for each element in this.getItems()
     *          |   if (!(element.getDirectory() == this))
     *          |       result == false
     *          | result == true
     */
    public boolean hasProperElements(){
        for (SystemItem element : items){
            if (!(element.getDirectory() == this)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the current directory is a root directory
     * @return true if the directory of this directory is null and if the root of this directory is this directory
     *         | result == ((directory == null) && (getRoot() == this))
     */
    public boolean isRoot(){
        return ((directory == null) && (getRoot() == this));
    }

    /**
     * Method for making a root directory given the name of the directory and whether the directory is writable.
     *
     * @effect a new directory is initialised with name and write access as given and directory set to null
     *         | this.Directory(name, null, writable)
     *
     */
    public Directory(String name, boolean writable) {
        this(name, null, writable);
    }

    /**
     * Return the size of the directory, how many items that are in this directory.
     */
    public int getNbItems(){
        return items.size();
    }

    /**
     * Return whether the index is in scope of the items in this directory.
     * @param index The index that we want to check whether it is in the list of items of the directory.
     * @return Return true if and only if the index is smaller than the size of the items.
     *        | result == items.NbItems() >= index
     */
    public boolean isValidIndex(int index){
        return getNbItems() >= index;
    }

    /**
     * If possible, return the item at place given by parameter index. We are not counting from 0 here.
     * The first element can be accessed with parameter 1. If the index is bigger than the list with items,
     * throw IndexOutOfBoundsException.
     * @param index The place we want the item from.
     * @return Item at index being input index-1.
     *        | result == items.get(index-1)
     * @throws IndexOutOfBoundsException
     *        The given index is too big for the list with items.
     *        | getNbItems() < index
     */
    public SystemItem getItem(int index) throws IndexOutOfBoundsException{
        if (!isValidIndex(index-1)){
            throw new IndexOutOfBoundsException();
        }
        return items.get(index-1);
    }

    /** Return the name for a new directory that was not given a valid name.
     *
     * @return   A valid directory name.
     *        | isValidName(result)
     */
    @Override
    public String getDefaultName(){
        return "new_directory";
    }

    /**
     * If there is an item in this directory with the same name as given, return this item. itemName is case-sensitive.
     * If there is not such item in this directory, throw filesystem.NotInMapException.
     *
     * @param itemName The name of the item we want to find in this directory.
     * @return item
     *         Return the item with the name being itemName.
     *        | result.getName() == itemName
     * @throws NotInMapException
     *        There is not an item in this directory with its name being itemName.
     *        | for each item in this.items:
     *        | !(item.getName() == itemName)
     */
    public SystemItem getItem(String itemName){
        for (SystemItem item : items) {
            if (Objects.equals(itemName, item.getName())) {
                return item;
            }
        }
        throw new NotInMapException(this, itemName);
    }

    /**
     * Return true if and only if there is an item in this directory with the same name as itemName, with itemName
     * not being case-sensitive.
     *
     * @param itemName The name we want to check whether there is an item in this directory with.
     * @return True if and only if there is an item in this directory with the same name as itemName, with itemName not
     * being case-sensitive
     *        | result == there is an item in this.items with item.getName().lowercase() == itemName.lowercase()
     */
    public boolean containDiskItemWithName(String itemName){
        String lowercaseItemName = itemName.toLowerCase(Locale.ROOT);
        for (SystemItem item : items) {
            if (Objects.equals(lowercaseItemName, item.getName().toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the item is in this directory, give the index, if not throw filesystem.NotInMapException.
     *
     * @param inputItem The item we want the index of.
     * @return The index of the inputItem in this directory.
     *        | getItemAt(index) == inputItem
     * @throws NotInMapException
     *        The item is not in this directory.
     *        | for each item in this.items():
     *        | !(inputItem == item)
     */
    public int getIndexOf(SystemItem inputItem){
        int index = 0;
        for (SystemItem item: items){
            if (inputItem == item){
                return index;
            }
            index++;
        }
        throw new NotInMapException(this, inputItem.getName());
    }

    /**
     * Return whether the inputItem is in this directory or not. This method also check if the inputItem is in
     * directories in this directory and further.
     *
     * @param inputItem The item we want to know if this item is in this directory.
     * @return True if the inputItem is directly or indirectly in this directory.
     *        | result == inputItem in getItems()
     */
    public boolean hasAsItem(SystemItem inputItem){
        if (items != null) {
            for (SystemItem item : items) {
                if (item instanceof Directory) {
                    if (((Directory) item).hasAsItem(inputItem)) {
                        return true;
                    }
                }
                if (item == inputItem) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove an item from its directory.
     * @param item
     *        The item we want to remove out of this directory.
     * @throws IllegalActionException
     *         If the item is not in this directory.
     *         | !(items.contains(item))
     * @post The item is removed from this directory.
     *       | items.remove(item)
     */
    protected void remove(SystemItem item){
        if (!(getItems().contains(item))){
            throw new IllegalActionException(item);
        }
        items.remove(item);

    }

    /**
     * Return a list of the items in this directory, this is list is in alphabetic order.
     */
    public List getItems(){
        return items;
    }

    /**
     * Check whether the given name is a legal name for a directory.
     *
     * @param  	name
     *			The name to be checked
     * @return	True if the given string is effective, not
     * 			empty and consisting only of letters, digits,
     * 			hyphens and underscores; false otherwise.
     * 			| result ==
     * 			|	(name != null) && name.matches("[a-zA-Z_0-9-]+")
     */
    @Override
    public boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9-]+"));
    }

    /**
     * Add an item to this directory.
     * @param item
     *        The item we want to add.
     * @throws IllegalActionException
     *         If the item cannot be added to this directory.
     *         | !isValidAddItem(item)
     * @throws AccessRightsException
     *         If writable is false.
     *         | !sWritable()
     * @effect Set the directory of the given item to this.
     *         | item.setDirectory(this)
     * @effect The item is added to the directory in alphabetic order.
     *       | addItemAlphabetic(item)
     */
    public void addItem(SystemItem item){
        if (!isWritable){
            throw new AccessRightsException(this);
        }
        if (!isValidAddItem(item)) {
            throw new IllegalActionException(item);
        }
        items.add(item); // Add this way cause alphabetic does not work yet
    }

    /**
     * Add an item to this directory in alphabetic order.
     * @param inputItem
     *        The item we want to add to this directory.
     * @post The item is added to this directory in alphabetic order.
     */
    protected void addItemAlphabetic(SystemItem inputItem){
        int index = 0;

        //Make a list with characters of the inputItem name.
        char[] chInput = new char[inputItem.getName().length()];
        for (int i = 0; i < inputItem.getName().length(); i++){
            chInput[i] = inputItem.getName().charAt(i);
            System.out.println("add character");
        }
        System.out.println(chInput);

        for (int m = 0; m < items.size(); m++){
            SystemItem item = (SystemItem) getItems().get(m);

            //Make a list with characters of an item from this.items
            char[] chItem = new char[inputItem.getName().length()];
            for (int i = 0; i < item.getName().length(); i++){
                chItem[i] = item.getName().charAt(i);
            }

            //Compare the letters and put in the list where needed
            for (int j = 0; j < inputItem.getName().length(); j++){
                if (j >= item.getName().length()){
                    items.add(index, inputItem);
                }
                else if (chInput[j] < chItem[j]){
                    items.add(index, inputItem);
                }
                else if (chInput[j] > chItem[j]){
                    index ++;
                    j = inputItem.getName().length() + 1; //This will break the for loop.
                }
            }
        }
    }

    /**
     * Check if the item is a valid item to add to this directory
     * @param item
     *        The item we want to check if it is valid.
     * @return true if the directory of the item is this directory and
     *         - in the case the given item is a directory - if the item doen't contain the directory in his list of elements
     *         | if (item instanceof Directory)
     *         |    result1 == (!item.hasAsItem(this))
     *         | result == result1 && (item.getDirectory() == this)
     */
    protected boolean isValidAddItem(SystemItem item){
        if (item.getDirectory() != this){
            return false;
        }
        if (item instanceof Directory){
            return !((Directory) item).hasAsItem(this);
        }
        return true;
    }

    /**
     * Returns the total disk usage of the directory
     * @return the sum of the total disk usages of all the items in the directory
     *         | for each item in this.getItems()
     *         |    result == result + item.getTotalDiskUsage()
     */
    @Override
    public int getTotalDiskUsage(){
        int totalDiskUsage = 0;
        for (SystemItem item : items){
            totalDiskUsage += item.getTotalDiskUsage();
        }
        return totalDiskUsage;
    }

    /**
     * Makes the directory a root directory.
     * @effect  The directory is moved from his associated directory.
     *          | this.getDirectory.remove(this)
     * @effect  The directory is set to null.
     *          | this.setDirectory == null
     *
     */
    public void makeRoot() {
        if (!isWritable()) {
            throw new AccessRightsException(this);
        }
        Directory parent = directory;
        parent.remove(this);
        this.setDirectory(null);
    }

    @Override
    public boolean isDirectOrIndirectChildOf(Directory directory){
        if (this.isRoot()) {
            return this == directory;
        }
        return super.isDirectOrIndirectChildOf(directory);
    }

}
