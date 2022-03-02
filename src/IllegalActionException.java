/**
 *
 */
public class IllegalActionException
extends RuntimeException {
    private final boolean writable;
    private final File file;

    public IllegalActionException(boolean writable, File file) {
        this.writable = writable;
        this.file = file;
    }

    public boolean isWritable() {
        return writable;
    }

    public File getFile() {
        return file;
    }

}
