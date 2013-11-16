package rule.spellout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class SimplePanel extends JPanel {
    
    private JLabel affixLabel;
    private JTextField affix;
    private JTextField gloss;

    public SimplePanel() {
        affixLabel = new JLabel("Prefix");
        affix = new JTextField(10);
        
        JLabel glossLabel = new JLabel("Gloss");
        gloss = new JTextField(10);
        
        setLayout(new MigLayout("wrap 2"));
        add(affixLabel);
        add(affix);
        add(glossLabel);
        add(gloss);
    }
    
    public void setModType(ModType type) {
        affixLabel.setText(type.toString());
    }
    
    public String getAffix() {
        return affix.getText();
    }
    
    public String getGloss() {
        return gloss.getText();
    }
}
