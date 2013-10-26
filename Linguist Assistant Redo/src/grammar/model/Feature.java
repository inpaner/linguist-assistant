package grammar.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.model.Language;

import commons.dao.DAOFactory;


public class Feature extends Node {
    
    public static Feature getEmpty(Constituent parent) {
        return new Feature(parent);
    }
    
    public static List<Feature> getAll(Constituent constituent) {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        return dao.getAllFeatures(constituent);
    }
    
    private Integer pk;
    private String name;
    private String value;
    private Language language;
    private Constituent parent;
    private String description;
    private static Map<String, List<String>> possibleValues = new HashMap<>();
    
    private Feature(Constituent parent) {
        this.parent = parent;
        parent.addFeature(this);
    }
    
    protected Feature(String name, String value, Constituent parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;
        level = parent.getLevel() + 1;
    }
    
    protected Feature(Integer pk, String aName, Constituent aParent) {
        this(aName, aParent);
        this.pk = pk;
    }
    
    protected Feature(String name, Constituent parent) {
        this(name, null, parent);
        value = getDefaultValue();
    }
    
    public Feature(String name) {
    	this.name = name;
        value = getDefaultValue();
    }
    
    public String getDefaultValue() {
        return getPossibleValues().get(0);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    protected void setValue(String value) {
        this.value = value;
    }

    public Constituent getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return name;
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
        List<String> values = possibleValues.get(name);
        if (values == null) {
            DAOFactory factory = DAOFactory.getInstance();
            FeatureDAO dao = new FeatureDAO(factory);
            values = dao.getPossibleValues(this);
            possibleValues.put(name, values);
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
        return pk;
    }
    
    void setPk(int pk) {
        this.pk = pk;
    }
}
