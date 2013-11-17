package rule.classic.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import ontology.model.Concept;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;
import rule.RuleUtils;
import rule.classic.InputCons;

public class InputDetailsPanel extends JPanel {
    private InputCons cons;
    private JLabel concept;
    private JTextArea featuresArea;
    
    public static void main(String[] args) {
        new MainFrame().setPanel(new InputDetailsPanel());
    }
    
    public interface Listener {
        void addConcept(InputCons cons);
        void deleteConcept(InputCons cons);
        void editFeatures(InputCons cons);
        void toggleOptional(InputCons cons);
    }
    
    public InputDetailsPanel() {
        JCheckBox optional = new JCheckBox("Optional");
        JLabel consLabel = new JLabel("Concept: ");
        concept = new JLabel("---");
        JButton addConcept = new JButton("+");
        JButton deleteConcept = new JButton("—");
        
        JLabel featuresLabel = new JLabel("Features: ");
        featuresArea = new JTextArea(3, 20);
        featuresArea.setEditable(false);
        JScrollPane featuresScroll = new JScrollPane(featuresArea);
        
        JButton editFeatures = new JButton("...");
        
        setLayout(new MigLayout("wrap 2"));
        add(optional, "wrap");
        add(new JSeparator(), "span, split, gapleft rel, growx, wrap");
        
        add(consLabel);
        add(concept);
        add(addConcept, "skip, span, split 2");
        add(deleteConcept);
        add(new JSeparator(), "span, split, gapleft rel, growx, wrap");
        
        add(featuresLabel, "span, split, wrap");
        add(featuresScroll, "span, split, wrap");
        add(editFeatures, "span, split, right");
    }
    
    public void setConstituent(InputCons cons) {
        this.cons = cons;
        Concept concept = cons.getConcept();
        if (concept == null) {
            this.concept.setText("---");
        }
        else {
            this.concept.setText(concept.toString());
        }
        String text = RuleUtils.getFeaturesText(cons.getFeaturesList());
        featuresArea.setText(text);
    }
}
