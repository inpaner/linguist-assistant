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
    private ArrayList<Rule> newRules = new ArrayList<>();
    private ArrayList<Rule> appliedRules = new ArrayList<>();
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
        
        boolean containsFeature = false;
        
        for (Feature feature : features) {
            if (toUpdate.equivalent(feature)) {
                containsFeature = true;
                break;
            }
        }
        
        if (!containsFeature) { 
            toUpdate.setValue(newValue);
            features.add(toUpdate);
            System.out.println("Feature " + toUpdate.getName() + " added to Constituent "+getLabel());
        }
        else if (setToDefault) {
            features.remove(toUpdate);
            System.out.println("Feature removed!");
        }
        else {
            toUpdate.setValue(newValue);
            System.out.println("Value changed!");
        }
    }
    

    public List<Feature> getAllFeatures() {
    	//System.out.println("Getting all features");
        List<Feature> result = new ArrayList<>(); 
        if (category == null) // root block
        {
        	return result;
        }
        
        //System.out.println(category.getName()+":"+category.getFeatures().size());
        for (Feature feature : category.getFeatures()) {
            boolean found = false;
            for (Feature ownFeature : features) {
                if (ownFeature.equivalent(feature)) {
                    result.add(ownFeature);
                    //System.out.println("Feature "+ownFeature+"added");
                    found = true;
                    break;
                }
                //else System.out.println("Feature "+ownFeature+" not in category ");
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
                && (oldIndex == index || oldIndex + 1 == index) ) {
            System.err.println("tried to move child to same index");
            return;
        }
        
        if (newChild.isAncestor(this)) {
            System.err.println("tried to move child to ancestor");
            return; 
        }
        if (newChild.parent != null) {
            newChild.parent.children.remove(newChild);
            newChild.parent = this;
        }
        if (oldIndex != -1 && oldIndex < index) {
            index--; // to account for prior removal from parent
        }
        
        try {
            children.add(index, newChild);
            System.out.println("successfully moved child");
        }
        catch (IndexOutOfBoundsException ex) {
            System.err.println("tried to move child outside of bounds");
            children.add(newChild);
            System.out.println(children.size());
        }
        
        newChild.parent = this;
    }
    
    public void moveToEnd(Constituent child) {
        moveChild(child, children.size());
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }
    
    public boolean evaluate(Rule rule) {
        
        boolean ruleAdded = false;
        for (Constituent child : children) {
            if (child.evaluate(rule.createPassedRule())) {
                ruleAdded = true;
            }
        }
        
        if (!appliedRules.contains(rule) && rule.evaluate(this)) {
            System.out.println("Adding: " + rule.toString());
            newRules.add(rule);
            ruleAdded = true;
        }
        else if (appliedRules.contains(rule)) {
            System.out.println(rule.toString() + " skipped because in ruleset already");
        }
        
        return ruleAdded;
    }
    
    
    public void applyRules() {
        for (Constituent child : children) {
            child.applyRules();
        }
        
        for (Rule rule : newRules) {
            rule.apply();
        }
        
        appliedRules.addAll(newRules);
        newRules.clear();
    }

    public Target getTarget() {
        if (target == null) {
            target = new Target();
        }
        
        return target;
    }
}
