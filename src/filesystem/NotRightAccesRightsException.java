package filesystem;

public class NotRightAccesRightsException extends RuntimeException{

    final private Directory directory;

    public NotRightAccesRightsException(Directory directory){

        this.directory = directory;

    }

    public Directory getDirectory() {
        return directory;
    }


}
