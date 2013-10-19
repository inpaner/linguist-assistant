package ontology.model;

import grammar.model.Constituent;
import grammar.model.Node;

public class Concept extends Node {
    private String name;
    private Constituent parent;
    
    public Concept(String name, Constituent parent) {
        this.name = name;
        this.parent = parent;
        fLevel = parent.getLevel() + 1;
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
