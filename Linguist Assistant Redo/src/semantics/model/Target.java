package semantics.model;

import lexicon.model.Entry;

public class Target {
    private Entry entry;
    private String prefix;
    private String suffix;
    private String translation;
    
    public Target() {
        prefix = "";
        translation = "";
        suffix = "";
    }
    
    public Target(String entry) {
    	this.setEntry(new Entry(entry));
    }
    
    public void setEntry(Entry entry) {
        this.entry = entry;
        translation = entry.getStem();
    }
    
    public String toString() {
        if (entry == null) return "";
        return prefix + translation + suffix;
    }
}
