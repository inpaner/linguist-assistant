package lexicon.model;

import grammar.model.Category;
import grammar.model.Feature;
import grammar.model.FeatureDAO;

import java.util.List;

import commons.dao.DAOFactory;

public class Entry {
    
    public static Entry getEmpty() {
        return new Entry();
    }
    
    public static Entry getInstance(int pk) {
        DAOFactory factory = DAOFactory.getInstance();
        EntryDAO dao = new EntryDAO(factory);
        return dao.retrieve(pk);
    }
    
    public static List<Entry> getAll(String substring, 
                Language language, Category category) {
        
        DAOFactory factory = DAOFactory.getInstance();
        EntryDAO dao = new EntryDAO(factory);
        return dao.retrieveAll(substring, language, category);
    }
    
    private int pk;
	private String stem;
	private String gloss;
	private Language language;
	private Category category;
	
	void setPk(int pk) {
	    this.pk = pk;
	}
	
	public int getPk() {
	    return pk;
	}
	
	public String getStem() {
		return stem;
	}
	
	public void setStem(String stem) {
		this.stem = stem;
	}
	
	public String getGloss() {
		return gloss;
	}
	
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}
	
	void setLanguage(Language language) {
	    this.language = language;
	}
	
	public Language getLanguage() {
	    return language;
	}
	
	protected void setConstituent(Category category) {
	    this.category = category;
	}
	
	public Category getConstituent() {
	    return category;
	}
	
	// TODO getComment
	public String getComment() {
	    return "";
	}
	
	public String getSampleSentence() {
	    return "";
	}
	
	@Override
	public String toString() {
	    return stem;
	}
	
	public List<Form> getForms() {
	    DAOFactory factory = DAOFactory.getInstance();
	    FormDAO dao = new FormDAO(factory);
	    return dao.retrieveAll(this);
	}
	
	public List<Feature> getFeatures() {
	    DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        return dao.retrieveAll(this);
	}
}
