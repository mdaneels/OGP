public class Link extends SystemItem {

    private final SystemItem reference;
    private boolean valid = true;

    public Link(String name, boolean writable, Directory directory, SystemItem reference) throws IllegalArgumentException {
        super(name, directory);
        if (!isValidReference(reference)) {
            throw new IllegalArgumentException("Invalid reference!");
        }
        this.reference = reference;
    }

    public boolean isValidReference(SystemItem reference){
        return (reference instanceof File) || (reference instanceof Directory);
    }

    public SystemItem getReference() {
        return reference;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    protected boolean isValidName(String name) {
        return (name != null && name.matches("[a-zA-Z_0-9.-]+"));
    }

    public String getDefaultName() {
        return "new_link";
    }
}
