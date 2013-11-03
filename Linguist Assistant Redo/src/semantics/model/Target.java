package semantics.model;

import lexicon.model.Entry;

public class Target {
    private Entry entry;
    private String prefix;
    private String suffix;
    
    public Target() {
        prefix = "";
        suffix = "";
    }
    
    public void setEntry(Entry entry) {
        this.entry = entry;
    }
    
    public String toString() {
        return prefix + entry.getStem() + suffix;
    }
}
