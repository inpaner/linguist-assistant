package model;

public class Concept extends Node {
    private String name;
    private Constituent parent;
    
    protected Concept(String name, Constituent parent) {
        this.name = name;
        this.parent = parent;
        level = parent.getLevel() + 1;
    }
    
    public Constituent getParent() {
        return parent;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void sysout() {
        String sl = String.valueOf(level + 1);
        
        String spaces = String.format("%" + sl + "s", ""); 
        System.out.println(spaces + name);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Concept))
            return false;
        
        return toString().equals(other.toString()) ? true : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
