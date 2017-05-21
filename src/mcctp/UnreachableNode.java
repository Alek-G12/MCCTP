package mcctp;

/**
 *
 * @author Alek_G12
 */
public class UnreachableNode {
    public int index;
    public boolean covered;

    public UnreachableNode(int index) {
        this.index = index;
        this.covered = false;
    }

    public int getIndex() {
        return index;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }
    
    
    

}
