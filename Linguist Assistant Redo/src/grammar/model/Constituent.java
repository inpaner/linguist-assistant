package grammar.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    
    public static List<Constituent> getAllConstituents() {
        if (allConstituents == null) {
            DAOFactory factory = DAOFactory.getInstance();
            ConstituentDAO dao = new ConstituentDAO(factory);
            
        }
        ArrayList<Constituent> allConstituents = new ArrayList<Constituent>();
        try {
            String query =
                    "SELECT SyntacticCategory.abbreviation AS synAbbr " +
                    "  FROM SemanticCategory " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk ";
            ResultSet rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                String abbr = rs.getString("synAbbr");
                Constituent constituent = new Constituent(abbr, null);
                constituent.fLevel = -2;
                allConstituents.add(constituent);
            }
            DBUtil.finishQuery();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allConstituents;
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
        
    }
    
    public Constituent(Constituent parent) {
        if (parent == null) {
            fLevel = 0;
        } else {
            fLevel = parent.getLevel() + 1;
        }
        this.parent = parent;
        children = new ArrayList<>();
        features = new ArrayList<>();
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
    }
    
    public List<Constituent> getChildren() {
        return children;
    }
    
    public boolean hasChildren() {
        return children.size() > 0;
    }
    
    public List<Feature> getFeatures() {
        return features;
    }
    
    protected void addFeature(Feature newFeature) {
        features.add(newFeature);
    }
    
    public void updateFeature(Feature toUpdate, String newValue) {
        boolean setToDefault = toUpdate.getDefaultValue().equals(newValue);
        if (!features.contains(toUpdate)) { // feature value previously set to default
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
    
    /**
     * Gets all features including those with default values.
     * 
     * @return The list of all features.
     */
    public List<Feature> getAllFeatures() {
        ArrayList<Feature> allFeatures = new ArrayList<Feature>();
        
        try {
            String query =
                    "SELECT Feature.name AS name " +
                    "  FROM Feature " +
                    "       JOIN SemanticCategory " +
                    "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + syntacticCategory + "'; ";

            ResultSet rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                String featureName = rs.getString("name");
                Feature feature = getFeature(featureName);
                allFeatures.add(feature);
            }
            DBUtil.finishQuery();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return allFeatures;
    }
   
    
    /**
     * Returns the requested feature.
     * @param name Name of the feature
     * @return The feature with the appropriate name
     */
    // TODO return null if feature is not valid
    private Feature getFeature(String name) {
        Feature valid = null;
        for (Feature feature : features) {
            if (feature.getName().equals(name)) {
                valid = feature;
                break;
            }
        }
        if (valid == null) {
            valid = new Feature(name, this);
        }
        return valid;
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
    
    // TODO __MASSIVE BUG check for equivalence
    public void moveChild(Constituent newChild, int index) {
        int oldIndex = children.indexOf(newChild);
        System.out.println(newChild.fLevel);
        System.out.println("index " + index);
        
        if (oldIndex != -1 && newChild.fLevel != -2
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
        return this.toString().hashCode();
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
    
    private String syntacticCategory;
    private String syntacticAbbreviation;
    private String semanticCategory;
    private String semanticAbbreviation;
    private String deepAbbreviation;
    private Constituent parent;
    private ArrayList<Constituent> children;
    private ArrayList<Feature> features;
    private Concept concept;
    private Translation translation;
    
    
}
