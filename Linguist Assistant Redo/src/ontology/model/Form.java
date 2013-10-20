package ontology.model;

import grammar.model.Constituent;
import grammar.model.ConstituentDAO;

import commons.dao.DAOFactory;

public class Form {
	private String name;
	private Integer pk;
	private int syntacticCategoryPK;
	private String syntacticCategory;
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
}
