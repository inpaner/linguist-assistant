package grammar.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.model.Form;
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
    private static List<Constituent> allConstituents;
    private static Map<Integer, List<Feature>> fPossibleFeatures = new HashMap<>();
    private static Map<Integer, List<Form>> fPossibleForms = new HashMap<>();
    
    public static void main(String[] args) {
        Constituent con = Constituent.getBySyntacticCategory("Noun");
        System.out.println(con.syntacticAbbreviation);
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
        clone.level = toCopy.level;
        
        return clone;
    }
    
    public static List<Constituent> getAll() {
        DAOFactory factory = DAOFactory.getInstance();
        ConstituentDAO dao = new ConstituentDAO(factory);
        allConstituents = dao.getAllConstituents();            
    
        return allConstituents;
    }
    
    public static Constituent getBySyntacticAbbr(String syntacticAbbr) {
        DAOFactory factory = DAOFactory.getInstance();
        ConstituentDAO dao = new ConstituentDAO(factory);
        return dao.retrieveBySyntacticAbbr(syntacticAbbr);
    }
    
    public static Constituent getBySyntacticCategory(String category) {
        DAOFactory factory = DAOFactory.getInstance();
        ConstituentDAO dao = new ConstituentDAO(factory);
        return dao.retrieveBySyntacticCategory(category);
    }
    
    private Integer pk;
    private String syntacticCategory;
    private String syntacticAbbreviation;
    private String semanticCategory;
    private String semanticAbbreviation;
    private String deepAbbreviation;
    private Constituent parent;
    private ArrayList<Constituent> children = new ArrayList<>();;
    private ArrayList<Feature> features = new ArrayList<>();;
    private ArrayList<Form> forms = new ArrayList<>();;
    
    private Concept concept;
    private Translation translation;
    
    
    public static Constituent getEmpty() {
        return new Constituent();
    }
    
    protected Constituent() {
        level = 0;
    }
    
    public Constituent(Constituent parent) {
        super();
        if (parent != null) {
            level = parent.getLevel() + 1;
        }
        
        this.parent = parent;
    }
    
    public void setLabel(String label) {
        this.syntacticCategory = label;
    }
    
    public String getLabel() {
        return syntacticCategory;
    }
    
    public Constituent getParent() {
        return parent;
    }
    
    public void addChild(Constituent child) {
        children.add(child);
        child.parent = this;
        child.level = level + 1;
    }
    
    public List<Constituent> getChildren() {
        return children;
    }
    
    public boolean hasChildren() {
        return children.size() > 0;
    }
    
    protected void addFeature(Feature newFeature) {
        features.add(newFeature);
    }
    
    public void updateFeature(Feature toUpdate, String newValue) {
        boolean setToDefault = toUpdate.getDefaultValue().equals(newValue);
        if (!features.contains(toUpdate)) { 
            toUpdate.setValue(newValue);
            features.add(toUpdate);
        }
        else if (setToDefault) {
            features.remove(toUpdate);
        }
        else {
            toUpdate.setValue(newValue);
        }
    }
    
    public boolean hasFeatures() {
        return features.size() > 0;
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
            for (Feature ownFeature : features) {
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
    
    public List<Form> getForms() {
        List<Form> allForms = new ArrayList<Form>();
        List<Form> possibleForms = fPossibleForms.get(pk);
        
        if (possibleForms == null) {
            DAOFactory factory = DAOFactory.getInstance();
            ConstituentDAO dao = new ConstituentDAO(factory);
            possibleForms = dao.getAllForms(this);
            fPossibleForms.put(pk, possibleForms);
        }
        
        for (Form Form : possibleForms) {
            boolean found = false;
            for (Form ownForm : forms) {
                if (ownForm.equals(Form)) {
                    allForms.add(ownForm);
                    found = true;
                    break;
                }
            }
            if (!found) {
                allForms.add(Form);
            }
           
        }
        return allForms;
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
    
    private boolean isAncestor(Constituent potentialAncestor) {
        System.out.println(parent);
        if (potentialAncestor.parent == null)
            return false;
        if (this.equals(potentialAncestor.parent))
            return true;
        return isAncestor(potentialAncestor.parent);
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
        }
        if (oldIndex != -1 && oldIndex < index) {
            index--; // to account for prior removal from parent
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
        newChild.level = level + 1;
    }
    
    @Override
    public String toString() {
        return syntacticCategory;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Constituent))
            return false;

        Constituent otherCon = (Constituent) other;
        
        if (level != otherCon.level)
            return false;
        
        if (syntacticCategory != otherCon.syntacticCategory)
            return false;
        
        if (parent == null || otherCon.parent == null) 
            return false;
        
        return parent.equals(otherCon.parent);
        
        
        /*// really questionable implementation <_<
        String falseName = syntacticCategory + level;
        return falseName.equals(otherCon.syntacticCategory + otherCon.level) 
                        ? true 
                        : false;*/
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode() + level;
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
        this.features = features;
    }
    
    void setPk(int aPk) {
        pk = aPk;
    }
    
    public Integer getPk() {
        return pk;
    }
    public void setTranslation(Translation t) {
    	translation=t; 
    }
    public Translation getTranslation() {
    	return translation;
    }

    
}
