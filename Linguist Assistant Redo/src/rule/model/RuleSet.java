package rule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rule.model.output.Output;
import semantics.model.Constituent;

public class RuleSet extends Rule {
    private List<Rule> rules = new ArrayList<>();
    private Rule appliedRule = new Rule();
    
    
    public void addRule(Rule rule) {
        rules.add(rule);
    }
    
    @Override
    public Rule createPassedRule() {
        RuleSet result = new RuleSet();
        result.name = this.name;
        
        for (Rule rule : this.rules) {
            result.rules.add(rule.createPassedRule());
        }
        
        return result;
    }
    
    
    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        for (Rule rule : rules) {
            if (rule.evaluate(constituent)) {
                appliedRule = rule;
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    public void apply() {
        appliedRule.apply();
    }
    
    public List<Rule> getRules() {
        return rules;
    }
}
