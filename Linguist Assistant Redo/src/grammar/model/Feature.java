package grammar.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commons.dao.DAOFactory;


public class Feature extends Node {
    private Integer fPk;
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
    
    
    protected Feature(Integer aPk, String aName, Constituent aParent) {
        this(aName, aParent);
        fPk = aPk;
    }
    
    protected Feature(String name, Constituent parent) {
        this(name, null, parent);
        value = getDefaultValue();
    }
    public Feature(String name) {
        this(name, null, null);
        value = getDefaultValue();
    }
    
    public String getDefaultValue() {
        return getPossibleValues().get(0);
    }
    
    public void setName(String aName) {
        fName = aName;
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
    
    public Integer getPk() {
        return fPk;
    }
}
