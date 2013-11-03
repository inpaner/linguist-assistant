package rule.input;

import grammar.model.Feature;

import java.util.List;

import semantics.model.Constituent;

public class HasFeatures extends Input {
    private List<Feature> features;
    
    public void addFeature(Feature feature) {
        features.add(feature);
    }

    
    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = true;        
        List<Feature> beingEvaluated = constituent.getFeatures();
        
        for (Feature feature : features) {
            if (!beingEvaluated.contains(feature)) {
                result = false;
                break;
            }
        }
        
        return result;
    }
    
    
}
