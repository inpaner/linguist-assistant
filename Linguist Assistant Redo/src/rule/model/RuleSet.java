package rule.model;

import java.util.ArrayList;
import java.util.List;

import semantics.model.Constituent;

public class RuleSet extends Rule {
    private List<Rule> rules = new ArrayList<>();
    
    public void addRule(Rule rule) {
        rules.add(rule);
    }
    
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        for (Rule rule : rules) {
            if (constituent.evaluate(rule)) {
                result = true;
            }
        }
        
        return result;
    }
    
    public List<Rule> getRules() {
        return rules;
    }
}
