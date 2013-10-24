package lexicon.model;

import java.util.List;

import grammar.model.Constituent;
import grammar.model.ConstituentDAO;

import commons.dao.DAOFactory;

public class Form {
	private String name;
	private Integer pk;
	private int syntacticCategoryPK;
	private String syntacticCategory;
	public Form(String name)
	{
		this.name=name;
	}
	public static Form getBySyntacticCategory(String category) {
        DAOFactory factory = DAOFactory.getInstance();
        FormDAO dao = new FormDAO(factory);
        
        return dao.getBySyntacticCategory(category);
        
    }
	void setPk(int aPk) {
        pk = aPk;
    }
	
	public void setSyntacticCategory(String syntacticCategory) {
        this.syntacticCategory = syntacticCategory;
    }
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public void saveToDB()
	{
		DAOFactory factory = DAOFactory.getInstance();
        FormDAO dao = new FormDAO(factory);
		dao.create(this);
	}
	public Integer getPK() {
		return pk;
	}
	
	public int getSyntacticCategoryPK() {
		return syntacticCategoryPK;
	}
	public void setSyntacticCategoryPK(int syntacticCategoryPK) {
		this.syntacticCategoryPK = syntacticCategoryPK;
	}
	
}
