package lexicon.model;

import org.apache.commons.lang3.text.WordUtils;

public class Language {
    
    /*
     * Static factories
     */
    
    public static Language getEmpty() {
        return new Language();
    }
    
    public static Language getInstance() {
        return new Language();
    }
    
    /*
     * Main class
     */
    
    private int pk;
    private String name;
    
    private Language() {}
    
    public int getPk() {
        return pk;
    }
    
    public void setPk(int pk) {
        this.pk = pk;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = WordUtils.capitalize(name);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Language))
            return false;
        
        Language otherLanguage = (Language) other;
        return name.equals(otherLanguage.name) ? true : false;
    }

}
