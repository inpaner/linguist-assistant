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
    public Target(String entry)
    {
    	this.setEntry(new Entry(entry));
    }
    public void setEntry(Entry entry) {
        this.entry = entry;
    }
    
    public String toString() {
        if (entry == null) return "";
        return prefix + entry.getStem() + suffix;
    }
}
