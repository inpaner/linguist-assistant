package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    public static void main(String[] args) {
        Constituent con = new Constituent("C", null);
    }
    
    public Constituent(String abbreviation, Constituent parent) {
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
                    " WHERE SyntacticCategory.abbreviation = '" + abbreviation + "'; ";
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
    
    public boolean hasFeatures() {
        return features.size() > 0;
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
}
