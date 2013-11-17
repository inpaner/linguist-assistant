package semantics.model;

import grammar.model.Category;
import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import ontology.model.Concept;
import rule.model.Rule;
import rule.model.RuleSet;



public class Constituent {
    private ArrayList<Constituent> children = new ArrayList<>();
    private ArrayList<Feature> features = new ArrayList<>();
    private ArrayList<Rule> rules = new ArrayList<>();
    private Constituent parent;
    private Category category;
    private Concept concept;
    private Target target;
    
    public static Constituent copy(Constituent toCopy) {
        Constituent clone = new Constituent();
        clone.category = Category.copy(toCopy.category);
        
        return clone;
    }
    
    public Constituent() {
    }
    
    public Constituent(Constituent parent) {
        this.parent = parent;
    }
    
    public void addChild(Constituent child) {
        children.add(child);
        child.parent = this;
    }
    
    public int level() {
        if (parent == null)
            return 0;
        
        return parent.level() + 1;
    }
    
    public Constituent getParent() {
        return parent;
    }
    
    public void setParent(Constituent parent) {
        this.parent = parent;
    }
    
    public List<Constituent> getChildren() {
        return children;
    }
    
    
    public boolean hasChildren() {
        return children.size() > 0;
    }
    
    
    public Category getCategory() {
        return category;
    }
    
    public String getLabel() {
        if (category == null)
            return "";
        return category.getLabel();
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
    
    public List<Feature> getFeatures() {
        return features;
    }
    
    public List<Feature> getAllFeatures() {
        List<Feature> result = new ArrayList<>(); 
        if (category == null) // root block
            return result;
        
        for (Feature feature : category.getFeatures()) {
            boolean found = false;
            for (Feature ownFeature : features) {
                if (ownFeature.equivalent(feature)) {
                    result.add(ownFeature);
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(feature);
            }
        }
        return result;
    }
    
    public boolean hasFeatures() {
        return features.size() != 0;
    }
    
    public boolean hasConcept() {
        return concept != null;
    }
    
    public Concept getConcept() {
        return concept;
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
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }
    
    public boolean evaluate(Rule rule) {
        boolean result = false;
        for (Constituent child : children) {
            child.evaluate(rule.createPassedRule());
        }
        
        if (rule.evaluate(this)) {
            rules.add(rule);
            result = true;
        }
        
        return result;
    }
    
    
    public void applyRules() {
        for (Constituent child : children) {
            child.applyRules();
        }
        for (Rule rule : rules) {
            rule.apply();
        }
    }

    public Target getTarget() {
        if (target == null) {
            target = new Target();
        }
        
        return target;
    }
}
