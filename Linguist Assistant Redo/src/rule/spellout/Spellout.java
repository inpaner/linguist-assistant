package rule.spellout;

import javax.swing.JPanel;

import rule.model.Rule;

public interface Spellout {
    Rule getRule();
    void loadRule(Rule rule);
    JPanel getView();
}
