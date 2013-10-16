package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Feature extends Node {
    private String name;
    private String value;
    private Constituent parent;
    
    protected Feature(String name, String value, Constituent parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;
        level = parent.getLevel() + 1;
    }
    
    
    /**
     * The constructor. Sets value to default.
     * 
     * @param name  Name of feature
     * @param parent  Constituent that contains the feature
     */
    protected Feature(String name, Constituent parent) {
        this(name, null, parent);
        value = getDefaultValue();
    } 
    
    public String getDefaultValue() {
        String defaultValue = null;
        try {
            String query = 
                    "SELECT FeatureValue.name AS name " +
                    "  FROM FeatureValue " +
                    "       JOIN Feature " +
                    "         ON FeatureValue.featurePk = Feature.pk " +
                    "       JOIN SemanticCategory " +
                    "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + parent.getSyntacticCategory() + "'  " +
                    "       AND " +
                    "       Feature.name = '" + name + "'" +
                    " LIMIT 1; ";

            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            defaultValue = rs.getString("name"); 
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    protected void setValue(String newValue) {
        value = newValue;
    }

    public Constituent getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void sysout() {
        String sl = String.valueOf(level + 1);
        
        String spaces = String.format("%" + sl + "s", ""); 
        System.out.println(spaces + name + " : " + value);
    }
    
    public static void main(String[] args) {
        Constituent cons = new Constituent(null);
        cons.setLabel("Clause");
        Feature feat = new Feature("type", "chocoloate", cons);
        cons.addFeature(feat);
        List<String> stuffs = feat.getPossibleValues();
        for (String stuff : stuffs) {
            System.out.println(stuff);
        }
    }
    
    public List<String> getPossibleValues() {
        ArrayList<String> values = new ArrayList<>();
        try {
            String query = 
                    "SELECT FeatureValue.name AS name " +
                    "  FROM FeatureValue " +
                    "       JOIN Feature " +
                    "         ON FeatureValue.featurePk = Feature.pk " +
                    "       JOIN SemanticCategory " +
                    "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + parent.getLabel() + "' " +
                    "       AND " +
                    "       Feature.name = '" + name + "';";
            ResultSet rs = DBUtil.executeQuery(query);
            while (rs.next()) {
                values.add(rs.getString("name"));
            }
            DBUtil.finishQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
    
    public boolean isDefault() {
        return value.equals(getDefaultValue());
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Feature))
            return false;
        
        return toString().equals(other.toString()) ? true : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    public void addNewValue(String string) {
        try {
            String query =
                    "SELECT Feature.pk AS pk " +
                    "  FROM Feature" +
                    "     JOIN SemanticCategory " +
                    "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + parent.getSyntacticCategory() + "' " +
                    "       AND Feature.name = '" + name + "'; ";
            
            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            int pk = rs.getInt("pk");
            DBUtil.finishQuery();
            String update =
                    "INSERT INTO FeatureValue(name, featurePk) " +
                    "values ('" + string + "', " + pk +")";
            System.out.println(update);
            DBUtil.executeUpdate(update);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
