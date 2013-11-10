package rule.model;

import java.util.ArrayList;
import java.util.List;

import semantics.model.Constituent;

public class RuleSet {
    private List<Rule> rules = new ArrayList<>();
    
    public void addRule(Rule rule) {
        rules.add(rule);
    }
    
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        for (Rule rule : rules) {
            if (rule.evaluate(constituent)) {
                result = true;
                break;
            }
        }
        
        return result;
    }
}
