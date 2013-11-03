package rule.input;

import java.util.List;

import semantics.model.Constituent;

public class And extends Input {
    private List<Input> rules;
    
    public void addRule(Input rule) {
        rules.add(rule);
    }
    
    /**
     * Short circuit AND evaluation
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        for (Input rule : rules) {
            if (rule.evaluate(constituent) == false)
                return false;
        }
        return true;
    }

}
