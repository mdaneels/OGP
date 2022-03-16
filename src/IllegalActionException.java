public class IllegalActionException extends RuntimeException {
    private final SystemItem item;

    public IllegalActionException(SystemItem item){
        this.item = item;
    }

    public SystemItem getItem() {
        return item;
    }
}
