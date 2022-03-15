public abstract class AbstractItem extends Item{

    public AbstractDirectory directory;

    protected AbstractItem(String name, boolean writable, AbstractDirectory directory){
        super(name, writable);
        setDirectory(directory);
    }

    public void setDirectory(AbstractDirectory directory){
        if (directory == null){
            throw new IllegalArgumentException("directory can not be null.");
        }
        this.directory = directory;
    }
}
