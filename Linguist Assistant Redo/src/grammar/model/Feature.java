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
    
    
    public static Feature get(Category category, String name, String value) {
        return new Feature(name, value, category);
    }
    
    
    
    public static List<Feature> getAll(Category category) {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        return dao.getAllFeatures(category);
    }
    
    public static Feature copy(Feature toCopy) {
        Feature result = getEmpty(toCopy.category);
        result.name = toCopy.name;
        result.value = toCopy.value;
        result.language = result.language;
        return result;
    }
    
    private Integer pk;
    private String name;
    private String value;
    private Language language;
    private Category category;
    private String description;
    private static Map<String, List<String>> possibleValues = new HashMap<>();
    
    private Feature(Category parent) {
        this.category = parent;
    }
    
    public Feature(String name, String value, Category parent) {
        this.name = name;
        this.value = value;
        this.category = parent;
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        pk = dao.getPk(this);
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
    	if(getPossibleValues() != null  &&  getPossibleValues().size()>0)
    		return getPossibleValues().get(0);
    	return "No value listed";
    }
    
    public Category getCategory() {
        return category;
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
        return category;
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
                && category.getName().equals(otherF.category.getName())
                        ) {
            
            
            if (language != null && language.equals(otherF.language)) {
                result = true;
            }
            else if (language == null) {
                result = true;
            }
        }
        
        return result;
    }
    
    public boolean equivalent(Feature other) {
        boolean result = false;
        if (name.equals(other.name)
                && category.getName().equals(other.category.getName())) {
            
            if (language != null && language.equals(other.language)) {
                result = true;
            }
            else if (language == null) {
                result = true;
            }
        }
        
        return result;
    }
    
    @Override
    public int hashCode() {
        int result = category.hashCode();
        if (name != null) {
            result += name.hashCode();
        }
        if (value != null) {
            result += value.hashCode();
        }
        if (language != null) {
            result += language.hashCode();
        }
        
        
        return result;
    }
    
    public Integer getPk() {
        return pk;
    }
    
    void setPk(int pk) {
        this.pk = pk;
    }
    

}
