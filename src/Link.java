public class Link extends AbstractItem {

    private final Item reference;
    private boolean valid = true;

    public Link(String name, boolean writable, AbstractDirectory directory, Item reference) throws IllegalArgumentException {
        super(name, writable, directory);
        if (!isValidReference(reference)) {
            throw new IllegalArgumentException("Invalid reference!");
        }
        this.reference = reference;
    }

    public boolean isValidReference(Item reference){
        return (reference instanceof File) || (reference instanceof AbstractDirectory);
    }

    public Item getReference() {
        return reference;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getDefaultName() {
        return "new_link";
    }
}
