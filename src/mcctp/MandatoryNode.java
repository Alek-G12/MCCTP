package mcctp;

/**
 *
 * @author Alek_G12
 */
class MandatoryNode {
    private int index;
    private boolean available;

    public MandatoryNode(int index) {
        this.index = index;
        this.available = true;
    }

    public int getIndex() {
        return index;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    
}
