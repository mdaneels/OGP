package filesystem;

// TE VERWIJDEREN, rootdirectory is een directory met directory null.
// Deze klasse is overbodig.
//

public class RootDirectory extends Directory{

    public RootDirectory(String name, boolean writable){
        super(name, null, writable);
    }

    /** Return the name for a new directory that was not given a valid name.
     *
     * @return   A valid directory name.
     *        | isValidName(result)
     */
    @Override
    public String getDefaultName(){
        return "new_root_directory";
    }

    /**
     * Check if the given directory is a valid directory for this root directory. This will only be valid if the
     * directory is null.
     * @param directory The directory we want to check if it is valid.
     * @return true if and only if the given directory is null.
     *        | result == (directory == null)
     */
    @Override
    public boolean canHaveAsDirectory(Directory directory){
        return directory == null;
    }

    @Override
    public void setDirectory(Directory directory){
        this.directory = null;
    }
}
