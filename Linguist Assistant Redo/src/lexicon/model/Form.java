package lexicon.model;

import java.util.List;

import grammar.model.Constituent;
import grammar.model.ConstituentDAO;

import commons.dao.DAOFactory;

public class Form {
    private int pk;
    private String name;
    private String value;
    private String description;
    private Language language;
    private Constituent constituent;
    
    
	public int getPk() {
        return pk;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Language getLanguage() {
        return language;
    }

    public Constituent getConstituent() {
        return constituent;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
    }

    public static Form getEmpty() {
	    return new Form();
	}
	
	private Form() {}
	
	public Form(String name) {
		this.name = name;
	}
	
	public static Form getBySyntacticCategory(String category) {
        DAOFactory factory = DAOFactory.getInstance();
        FormDAO dao = new FormDAO(factory);
        
        return null;
        
    }
	
	void setPk(int aPk) {
        pk = aPk;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void saveToDB() {
		DAOFactory factory = DAOFactory.getInstance();
        FormDAO dao = new FormDAO(factory);
		dao.create(this);
	}
	
	public Integer getPK() {
		return pk;
	}
	
	
}
