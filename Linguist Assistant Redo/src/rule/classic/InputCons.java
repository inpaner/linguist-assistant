package rule.classic;

import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import ontology.model.Concept;
import rule.model.input.Input;
import rule.model.output.Output;
import semantics.model.Constituent;

public class InputCons extends Constituent {
    
    String key = "root";
    boolean optional = false;
    boolean notPresent = false;
    
    // TODO multiple concepts
    // List<Concept> conceptInputs = new ArrayList<>();
    
    List<List<Feature>> featureInputs = new ArrayList<>();
        
    public InputCons() {
        
    }
    
    public Input generateInput() {
        // HasCategory
        
        // HasConcept
        
        // HasFeatures
        
        // HasWord
        
        // HasAffix
        
        // Get children rules 
        
        // Has children structure
        
        return null;
    }

    public List<List<Feature>> getFeatureInputs() {
        // TODO Auto-generated method stub
        return featureInputs;
    }
    
    public void setFeatureInputs(List<List<Feature>> list) {
        featureInputs = list;
    }
    
    public void setOptional(boolean optional) {
        this.optional = optional;
    }
    
    public boolean isOptional() {
        return optional;
    }
    
}
