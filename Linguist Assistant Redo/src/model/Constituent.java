package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constituent extends Node {
    private String label;
    private Constituent parent;
    private ArrayList<Constituent> children;
    private ArrayList<Feature> features;
    private Concept concept;
    private Translation translation;
    
    public Constituent(Constituent parent) {
        if (parent == null) {
            level = 0;
        } 
        else {
            level = parent.getLevel() + 1;
        }
        
        children = new ArrayList<>();
        features = new ArrayList<>();
    }
    
    //TODO protected
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
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
        System.out.println(result + label);
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
        return label;
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
