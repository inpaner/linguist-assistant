package grammar.model;

public abstract class Node {
    protected Integer fLevel;
    
    void setLevel(Integer aLevel) {
        fLevel = aLevel;
    }
    
    public int getLevel() {
        return fLevel;
    }
}
