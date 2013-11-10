package rule.model.input;

import java.util.ArrayList;
import java.util.List;

import rule.model.Rule;
import semantics.model.Constituent;

public class And extends Input {
    private List<Input> conditions = new ArrayList<>();
    
    public void addRule(Input rule) {
        conditions.add(rule);
    }
    
    /**
     * Short circuit AND evaluation
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        for (Input rule : conditions) {
            if (rule.evaluate(constituent) == false)
                return false;
        }
        return true;
    }

    @Override
    public void setRoot(Rule root) {
        super.setRoot(root);
        for (Input rule : conditions) {
            rule.setRoot(root);
        }
    }

}
