package grammar.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commons.dao.DAOFactory;
import commons.dao.DBUtil;

public class Feature extends Node {
    private String fName;
    private String value;
    private Constituent parent;
    private static Map<String, List<String>> fPossibleValues;
    
    static {
        fPossibleValues = new HashMap<>();
    }
    
    protected Feature(String name, String value, Constituent parent) {
        this.fName = name;
        this.value = value;
        this.parent = parent;
        fLevel = parent.getLevel() + 1;
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
                    "       Feature.name = '" + fName + "'" +
                    " LIMIT 1; ";

            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            defaultValue = rs.getString("name"); 
            DBUtil.finishQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    
    public String getName() {
        return fName;
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
        return fName;
    }
    
    public void sysout() {
        String sl = String.valueOf(fLevel + 1);
        
        String spaces = String.format("%" + sl + "s", ""); 
        System.out.println(spaces + fName + " : " + value);
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
        List<String> values = fPossibleValues.get(fName);
        if (values == null) {
            DAOFactory factory = DAOFactory.getInstance();
            FeatureDAO dao = new FeatureDAO(factory);
            values = dao.getPossibleValues(this);
            fPossibleValues.put(fName, values);
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
}
