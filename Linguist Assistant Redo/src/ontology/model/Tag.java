package ontology.model;

import grammar.model.ConstituentDAO;

import commons.dao.DAOFactory;

public class Tag {
    private Integer fPk;
    private String fName;
    
    public static Tag getInstance(int aPk) {
        DAOFactory factory = DAOFactory.getInstance();
        TagDAO dao = new TagDAO(factory);
        return dao.retrieve(aPk);
    }
    
    public static Tag getInstance(String aName) {
        DAOFactory factory = DAOFactory.getInstance();
        TagDAO dao = new TagDAO(factory);
        return dao.retrieve(aName);
    }
    
    Tag(int aPk, String aName) {
        fPk = aPk;
        fName = aName;
    }
    
    public Integer getPk() {
        return fPk;
    }
    
    public String getName() {
        return fName;
    }
    
    public void setName(String aName) {
        fName = aName;
    }
}
