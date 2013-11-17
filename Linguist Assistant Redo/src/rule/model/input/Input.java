package rule.model.input;

import rule.model.Rule;
import semantics.model.Constituent;

public abstract class Input {
    protected Rule root;
    protected boolean optional = false;
    
    
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public abstract boolean evaluate(Constituent constituent);
    
    public Rule getRoot() {
        return root;
    }
    public void setRoot(Rule root) {
        this.root = root;
    }
}
