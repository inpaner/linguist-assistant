package rule.model.output;

import rule.model.Rule;

public abstract class Output {
    protected Rule root;
    protected String key = "root";
    
    public abstract void apply();
    public void setRoot(Rule root) {
        this.root = root;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
