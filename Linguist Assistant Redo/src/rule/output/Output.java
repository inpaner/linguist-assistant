package rule.output;

import rule.Rule;

public abstract class Output {
    protected Rule root;
    
    public abstract void apply();
    public void setRoot(Rule root) {
        this.root = root;
    }
}
