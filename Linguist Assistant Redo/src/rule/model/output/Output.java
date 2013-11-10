package rule.model.output;

import rule.model.Rule;

public abstract class Output {
    protected Rule root;
    
    public abstract void apply();
    public void setRoot(Rule root) {
        this.root = root;
    }
}
