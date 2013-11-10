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
    
    public Entry getEntry() {
        return entry;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getTranslation() {
        return translation;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String toString() {
        if (entry == null) return "";
        return getPrefix() + getTranslation() + getSuffix();
    }
}
