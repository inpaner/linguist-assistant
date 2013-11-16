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

    public String getTranslation() {
        return translation;
    }
    
    public void setAffix(Affix affix, String value) {
        switch (affix) {
            case CIRUMFIX:
                break;
            case INFIX:
                break;
            case PREFIX:
                prefix = value;
                break;
            case SUFFIX:
                suffix = value;
                break;
            default:
                break;
        }
    }
    
    public String getAffix(Affix affix) {
        String result = "";
        
        switch (affix) {
            case CIRUMFIX:
                break;
            case INFIX:
                break;
            case PREFIX:
                result = prefix;
                break;
            case SUFFIX:
                result = suffix;
                break;
            default:
                ;
        }
        
        return result;
    }
    
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String toString() {
        return prefix + getTranslation() + suffix;
    }
}
