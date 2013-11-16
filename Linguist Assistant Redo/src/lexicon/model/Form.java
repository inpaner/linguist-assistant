package lexicon.model;

import grammar.model.Category;
import commons.dao.DAOFactory;

public class Form {
    private int pk;
    private String name;
    private String value;
    private String description;
    private Language language;
    private Category category;
    
    
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

    public Category getCategory() {
        return category;
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

    public void setConstituent(Category category) {
        this.category = category;
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
	
	@Override
	public String toString() {
	    String result = name;
	    if (value != null && !value.isEmpty()) {
	        result = " - " + value;
	    }
	    return result;
	}
}
