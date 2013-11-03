package rule.input;

import rule.Rule;
import semantics.model.Constituent;

public abstract class Input {
    protected Rule root;
    
    public abstract boolean evaluate(Constituent constituent);
    public void setRoot(Rule root) {
        this.root = root;
    }
}
