package rule.spellout.view;

import java.util.List;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rule.spellout.ModType;
import net.miginfocom.swing.MigLayout;

public class SimpleUi extends JPanel {
    
    private ModificationPanel modification;
    private BaseFormPanel base;
    private JLabel affixLabel;
    private JTextField affix;
    private JTextField gloss;
    
    public interface Listener extends ModificationPanel.Listener, BaseFormPanel.Listener {}
    
    public SimpleUi(Category category) {
        modification = new ModificationPanel();
        base = new BaseFormPanel(category);
        
        affixLabel = new JLabel("Prefix");
        affix = new JTextField(10);
        
        JLabel glossLabel = new JLabel("Gloss");
        gloss = new JTextField(10);
        
        setLayout(new MigLayout("wrap 2"));
        add(modification, "span, split, wrap");
        add(base, "span, split, wrap");
        
        add(affixLabel);
        add(affix);
        add(glossLabel);
        add(gloss);   
    }
    
    /*
     * Facade methods for Modification and Base Form Panels
     */
    public void setFeatures(List<List<Feature>> features) {
        base.setFeatures(features);
    }
    
    /*
     * Getters and setters for internal components
     */
    public void setModType(ModType type) {
        affixLabel.setText(type.toString());
    }
    
    public String getAffix() {
        return affix.getText();
    }
    
    public String getGloss() {
        return gloss.getText();
    }
    
    public void addListener(Listener listener) {
        modification.addListener(listener);
        base.addListener(listener);
    }
    
}
