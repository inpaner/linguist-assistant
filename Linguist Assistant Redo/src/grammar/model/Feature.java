package grammar.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.model.Language;

import commons.dao.DAOFactory;


public class Feature {
    
    public static Feature getEmpty(Category parent) {
        return new Feature(parent);
    }
    
    public static List<Feature> getAll(Category category) {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        return dao.getAllFeatures(category);
    }
    
    private Integer pk;
    private String name;
    private String value;
    private Language language;
    private Category parent;
    private String description;
    private static Map<String, List<String>> possibleValues = new HashMap<>();
    
    private Feature(Category parent) {
        this.parent = parent;
    }
    
    public Feature(String name, String value, Category parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;
    }
    
    protected Feature(Integer pk, String aName, Category aParent) {
        this(aName, aParent);
        this.pk = pk;
    }
    
    protected Feature(String name, Category parent) {
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
    
    public void setValue(String value) {
        this.value = value;
    }

    public Category getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public static void main(String[] args) {
        Category cons = Category.getByName("Clause");
        Feature feat = new Feature("type", "chocoloate", cons);
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
        
        Feature otherF = (Feature) other;
        boolean result = false;
        // Must be equal: name, value, language, category name
        if (name.equals(otherF.name)
                && value.equals(otherF.value)
                && parent.getName().equals(otherF.parent.getName())) {
            
            if (language != null && language.equals(otherF.language)) {
                result = true;
            }
            else {
                result = true;
            }
        }
        
        return result;
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
