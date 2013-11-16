package rule;

import grammar.model.Feature;

import java.util.List;

import rule.model.input.And;
import rule.model.input.HasFeature;
import rule.model.input.Input;
import rule.model.input.Or;

public class RuleUtils {
    public static Input getInput(List<List<Feature>> featuresList) {
        if (featuresList == null || featuresList.isEmpty())
            return null;
        
        And result = new And();
        
        for (List<Feature> features : featuresList) {
            Or or = new Or();
            for (Feature feature : features) {
                HasFeature hasFeature = new HasFeature(feature);
                or.addRule(hasFeature);
            }
            result.addRule(or);
        }
        
        return result;
    }
    
}
