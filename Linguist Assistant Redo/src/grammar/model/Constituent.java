package grammar.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ontology.model.Concept;
import commons.dao.DAOFactory;
import commons.dao.DBUtil;

/**
 * Blah di blah
 * 
 * @author Ivan
 * 
 */

public class Constituent extends Node {
    private static List<Constituent> fAllConstituents;
    private static Map<Integer, List<Feature>> fPossibleFeatures;
    
    static {
        fPossibleFeatures = new HashMap<>();
    }
    
    // TODO implement properly with copying of features
    public static Constituent copy(Constituent toCopy) {
        Constituent clone = new Constituent();
        clone.pk = toCopy.pk;
        clone.deepAbbreviation = toCopy.deepAbbreviation;
        clone.semanticCategory = toCopy.semanticCategory;
        clone.semanticAbbreviation = toCopy.semanticAbbreviation;
        clone.syntacticCategory = toCopy.syntacticCategory;
        clone.syntacticAbbreviation = toCopy.syntacticAbbreviation;
        clone.fLevel = toCopy.fLevel;
        
        return clone;
    }
    
    public static List<Constituent> getAllConstituents() {
        if (fAllConstituents == null) {
            DAOFactory factory = DAOFactory.getInstance();
            ConstituentDAO dao = new ConstituentDAO(factory);
            fAllConstituents = dao.getAllConstituents();            
        }
        
        return fAllConstituents;
    }
    
    public static Constituent get(String syntacticAbbr) {
        DAOFactory factory = DAOFactory.getInstance();
        ConstituentDAO dao = new ConstituentDAO(factory);
        return dao.getBySyntacticAbbr(syntacticAbbr);
    }
    
    public Constituent(String syntacticAbbr, Constituent parent) {
        this(parent); 
        try {
            String query =
                    "SELECT SemanticCategory.name AS semName, " +
                    "       SemanticCategory.abbreviation AS semAbbr, " +
                    "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
                    "       SyntacticCategory.name AS synName, " +
                    "       SyntacticCategory.abbreviation AS synAbbr " +
                    "  FROM SemanticCategory " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.abbreviation = '" + syntacticAbbr + "'; ";
            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            syntacticCategory = rs.getString("synName");
            syntacticAbbreviation = rs.getString("synAbbr");
            semanticCategory = rs.getString("semName");
            semanticAbbreviation = rs.getString("semAbbr");
            deepAbbreviation = rs.getString("deepAbbr");
            
            DBUtil.finishQuery();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    Constituent() {
        this(null);
    }
    
    public Constituent(Constituent parent) {
        if (parent == null) {
            fLevel = 0;
        } else {
            fLevel = parent.getLevel() + 1;
        }
        this.parent = parent;
        children = new ArrayList<>();
        fFeatures = new ArrayList<>();
    }
    
    //TODO protected
    public void setLabel(String label) {
        this.syntacticCategory = label;
    }
    
    public String getLabel() {
        return syntacticCategory;
    }
    
    public Constituent getParent() {
        return parent;
    }
    
    //TODO to protected
    public void addChild(Constituent child) {
        children.add(child);
        child.parent = this;
        child.fLevel = fLevel + 1;
    }
    
    public List<Constituent> getChildren() {
        return children;
    }
    
    public boolean hasChildren() {
        return children.size() > 0;
    }
    
    protected void addFeature(Feature newFeature) {
        fFeatures.add(newFeature);
    }
    
    public void updateFeature(Feature toUpdate, String newValue) {
        boolean setToDefault = toUpdate.getDefaultValue().equals(newValue);
        if (!fFeatures.contains(toUpdate)) { 
            toUpdate.setValue(newValue);
            fFeatures.add(toUpdate);
        }
        else if (setToDefault) {
            fFeatures.remove(toUpdate);
        }
        else {
            toUpdate.setValue(newValue);
        }
    }
    
    public boolean hasFeatures() {
        return fFeatures.size() > 0;
    }
    
    public List<Feature> getFeatures() {
        List<Feature> allFeatures = new ArrayList<Feature>();
        List<Feature> possibleFeatures = fPossibleFeatures.get(pk);
        
        if (possibleFeatures == null) {
            DAOFactory factory = DAOFactory.getInstance();
            ConstituentDAO dao = new ConstituentDAO(factory);
            possibleFeatures = dao.getAllFeatures(this);
            fPossibleFeatures.put(pk, possibleFeatures);
        }
        
        for (Feature feature : possibleFeatures) {
            boolean found = false;
            for (Feature ownFeature : fFeatures) {
                if (ownFeature.equals(feature)) {
                    allFeatures.add(ownFeature);
                    found = true;
                    break;
                }
            }
            if (!found) {
                allFeatures.add(feature);
            }
        }
        return allFeatures;
    }
   
    protected void setConcept(Concept concept) {
        this.concept = concept;
    }
    
    public Concept getConcept() {
        return concept;
    }
    
    public boolean hasConcept() {
        return concept != null;
    }
    
    private boolean isAncestor(Constituent potential) {
        System.out.println(parent);
        if (potential.parent == null)
            return false;
        if (this.equals(potential.parent))
            return true;
        return isAncestor(potential.parent);
    }
    
    public void moveChild(Constituent newChild, int index) {
        System.out.println(newChild.parent == null);
        int oldIndex = children.indexOf(newChild);
        System.out.println("Old index = " + oldIndex);
        System.out.println("New index = " + index);
        
        if (oldIndex != -1 
                && (oldIndex == index || oldIndex + 1 == index) ) { // child is moved to same place 
            System.out.println("1");
            return;
        }
        
        if (newChild.isAncestor(this)) {
            System.out.println("2");
            return; 
        }
        
        if (newChild.parent != null) {
            newChild.parent.children.remove(newChild);
            newChild.parent = null;
            System.out.println("3");
        }
        if (oldIndex != -1 && oldIndex < index) {
            index--; // to account for prior removal from parent
            System.out.println("4");
        }
        
        try {
            children.add(index, newChild);
            System.out.println("5");
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("6");
            children.add(newChild);
            System.out.println(children.size());
        }
        
        newChild.parent = this;
        newChild.fLevel = fLevel + 1;
    }
    
    @Override
    public String toString() {
        return syntacticCategory;
    }
    
    
    //TODO define Constituent.equals properly
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Constituent))
            return false;
        
        // really questionable implementation <_<
        String falseName = syntacticCategory + fLevel;
        Constituent otherCon = (Constituent) other;
        return falseName.equals(otherCon.syntacticCategory + otherCon.fLevel) 
                        ? true 
                        : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode() + fLevel;
    }

    public String getSyntacticCategory() {
        return syntacticCategory;
    }
    
    public void addNewFeature(String string) {
        try {
            String query =
                    "SELECT SemanticCategory.pk AS pk" +
                    "  FROM SemanticCategory " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + syntacticCategory + "'; ";
            
            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            int pk = rs.getInt("pk");
            DBUtil.finishQuery();
            String update =
                    "INSERT INTO Feature(name, semanticCategoryPk) " +
                    "values ('" + string +"', "+ pk +")";
            DBUtil.executeUpdate(update);
            
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getSyntacticAbbreviation() {
        return syntacticAbbreviation;
    }

    public String getSemanticCategory() {
        return semanticCategory;
    }

    public String getSemanticAbbreviation() {
        return semanticAbbreviation;
    }

    public String getDeepAbbreviation() {
        return deepAbbreviation;
    }

    public void setSyntacticCategory(String syntacticCategory) {
        this.syntacticCategory = syntacticCategory;
    }

    public void setSyntacticAbbreviation(String syntacticAbbreviation) {
        this.syntacticAbbreviation = syntacticAbbreviation;
    }

    public void setSemanticCategory(String semanticCategory) {
        this.semanticCategory = semanticCategory;
    }

    public void setSemanticAbbreviation(String semanticAbbreviation) {
        this.semanticAbbreviation = semanticAbbreviation;
    }

    public void setDeepAbbreviation(String deepAbbreviation) {
        this.deepAbbreviation = deepAbbreviation;
    }

    public void setParent(Constituent parent) {
        this.parent = parent;
    }

    public void setChildren(ArrayList<Constituent> children) {
        this.children = children;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.fFeatures = features;
    }
    
    void setPk(int aPk) {
        pk = aPk;
    }
    
    public Integer getPk() {
        return pk;
    }
    public void setTranslation(Translation t)
    {
    	translation=t;
    }
    public Translation getTranslation()
    {
    	return translation;
    }
    private Integer pk;
    private String syntacticCategory;
    private String syntacticAbbreviation;
    private String semanticCategory;
    private String semanticAbbreviation;
    private String deepAbbreviation;
    private Constituent parent;
    private ArrayList<Constituent> children;
    private ArrayList<Feature> fFeatures;
    private Concept concept;
    private Translation translation;
    
    
}
