package lexicon.model;

import grammar.model.CategoryDAO;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import commons.dao.DAOFactory;

public class Language {
    
    /*
     * Static factories
     */
    
    
    private static Language currentLanguage = Language.getInstance("Filipino");
    
    public static Language getCurrent() {
        return currentLanguage;
    }
    
    public static void main(String[] args) {
        for (Language item : Language.getAll()) {
            System.out.println(item);
        }
    }
    
    public static List<Language> getAll() {
        DAOFactory factory = DAOFactory.getInstance();
        LanguageDAO dao = new LanguageDAO(factory);
        return dao.retrieveAll();
    }

    public static Language getEmpty() {
        return new Language();
    }
    
    public static Language getInstance(int pk) {
        DAOFactory factory = DAOFactory.getInstance();
        LanguageDAO dao = new LanguageDAO(factory);
        return dao.retrieve(pk);
    }
    
    public static Language getInstance(String name) {
        DAOFactory factory = DAOFactory.getInstance();
        LanguageDAO dao = new LanguageDAO(factory);
        return dao.retrieve(name);
    }
    
    
    
    /*
     * Main class
     */
    
    private int pk;
    private String name;
    private String description = "";
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    @Override
    public String toString() {
        return name;
    }
    
}
