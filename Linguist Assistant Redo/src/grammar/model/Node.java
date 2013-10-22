package grammar.model;

public abstract class Node {
    protected Integer level;
    
    void setLevel(Integer level) {
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }
}
