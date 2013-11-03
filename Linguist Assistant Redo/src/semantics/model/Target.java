package semantics.model;

import lexicon.model.Entry;

public class Target {
    private Entry entry;
    private String prefix;
    private String suffix;
    
    public String toString() {
        return prefix + entry.getStem() + suffix;
    }
}
