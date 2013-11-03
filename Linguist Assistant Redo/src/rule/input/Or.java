package rule.input;

import java.util.List;

import rule.Rule;
import semantics.model.Constituent;

public class Or extends Input {
    private List<Input> conditions;
    
    public void addRule(Input rule) {
        conditions.add(rule);
    }
    
    /**
     * Short circuit OR evaluation
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        for (Input rule : conditions) {
            if (rule.evaluate(constituent) == true)
                return true;
        }
        return false;
    }


    @Override
    public void setRoot(Rule root) {
        super.setRoot(root);
        for (Input rule : conditions) {
            rule.setRoot(root);
        }
    }
}
