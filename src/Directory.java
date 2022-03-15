import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Class representing a directory, having a list of items.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public class Directory extends AbstractDirectory{

    public AbstractDirectory directory;

    @Raw
    public Directory(String name, boolean writable, Directory directory){
        super(name, writable);
        setDirectory(directory);
    }

    public void setDirectory(AbstractDirectory directory){
        if (directory == null){
            throw new IllegalArgumentException();
        }
        if (this.hasAsItem(directory)){
            throw new IllegalArgumentException("The given directory is in the directory.");
        }
        this.directory = directory;
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
    protected static boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9-]+"));
    }

}
