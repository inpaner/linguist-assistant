package rule.input;

import java.util.List;

import semantics.model.Constituent;

public class Or extends Input {
    private List<Input> rules;
    
    public void addRule(Input rule) {
        rules.add(rule);
    }
    
    /**
     * Short circuit OR evaluation
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        for (Input rule : rules) {
            if (rule.evaluate(constituent) == true)
                return true;
        }
        return false;
    }

}
