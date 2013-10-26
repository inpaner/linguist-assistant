package lexicon.model;

import grammar.model.Constituent;

import java.util.ArrayList;
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
                Language language, Constituent constituent) {
        
        DAOFactory factory = DAOFactory.getInstance();
        EntryDAO dao = new EntryDAO(factory);
        return dao.retrieveAll(substring, language, constituent);
    }
    
    private int pk;
	private String stem;
	private String gloss;
	private Language language;
	private Constituent constituent;
	
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
	
	protected void setConstituent(Constituent constituent) {
	    this.constituent = constituent;
	}
	
	public Constituent getConstituent() {
	    return constituent;
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
}
