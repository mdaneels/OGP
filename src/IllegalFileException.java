public class IllegalFileException extends RuntimeException{

    private File file;

    public IllegalFileException(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }
}
