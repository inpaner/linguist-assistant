package ontology.model;

import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;

public class Tag {
    private Integer fPk;
    private String name;
    
    public static Tag getInstance(int aPk) {
        DAOFactory factory = DAOFactory.getInstance();
        TagDAO dao = new TagDAO(factory);
        return dao.retrieve(aPk);
    }
    
    public static Tag getTagAll() {
        return new Tag(0, "All");
    }
    
    public static List<Tag> getAllTags() {
        List<Tag> result = new ArrayList<>();
        result.add(getTagAll());
        DAOFactory factory = DAOFactory.getInstance();
        TagDAO dao = new TagDAO(factory);
        result.addAll(dao.retrieveAll());
        return result;
    }
    
    public static Tag getInstance(String aName) {
        DAOFactory factory = DAOFactory.getInstance();
        TagDAO dao = new TagDAO(factory);
        return dao.retrieve(aName);
    }
    
    Tag(int aPk, String aName) {
        fPk = aPk;
        name = aName;
    }
    
    public Integer getPk() {
        return fPk;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Tag))
            return false;
        
        Tag otherTag = (Tag) other;
        return name.equals(otherTag.name);
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
