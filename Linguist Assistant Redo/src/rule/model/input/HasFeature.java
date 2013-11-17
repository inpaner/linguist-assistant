package rule.model.input;

import grammar.model.Feature;

import java.util.List;

import semantics.model.Constituent;

public class HasFeature extends Input {
    private Feature feature;
    
    public HasFeature(Feature feature) {
        setFeature(feature);
    }
    
    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    
    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = true;        
        
        if (!constituent.getAllFeatures().contains(feature)) {
            result = false;    
        }
        
        return result;
    }
    
    
}
