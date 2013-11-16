package rule.spellout;

import rule.model.Rule;

public interface Spellout {
    Rule getRule();
    void loadRule(Rule rule);
}
