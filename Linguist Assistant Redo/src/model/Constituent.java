package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Blah di blah
 * 
 * @author Ivan
 * 
 */
public class Constituent extends Node {
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
    
    public static List<Constituent> getAllConstituents() {
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
    
    public Constituent(Constituent parent) {
        if (parent == null) {
            level = 0;
        } else {
            level = parent.getLevel() + 1;
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
    
    public void moveChild(Constituent newChild, int index) {
        int oldIndex = children.indexOf(newChild);
        if (oldIndex == index || oldIndex + 1 == index) { // child is moved to same place 
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
            newChild.parent = this;
        }
        catch (IndexOutOfBoundsException ex) {
            children.add(newChild);
            newChild.parent = this;
            System.out.println(children.size());
        }
    }
    
    // TODO remove
    public void sysout() {
        char[] chars = new char[level + 1];
        Arrays.fill(chars, '-');
        String result = new String(chars);
        System.out.println(result + syntacticCategory);
        for (Feature feature : features) {
            feature.sysout();
        }
        if (concept != null) {
            concept.sysout();
        }
        for (Constituent constituent : children) {
            constituent.sysout();
        }
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
        
        return toString().equals(other.toString()) ? true : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String getSyntacticCategory() {
        return syntacticCategory;
    }
}
