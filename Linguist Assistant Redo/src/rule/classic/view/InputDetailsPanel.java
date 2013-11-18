package rule.classic.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private List<Listener> listeners = new ArrayList<>();
    private JCheckBox optional;
    
    public static void main(String[] args) {
        new MainFrame().setPanel(new InputDetailsPanel());
    }
    
    public interface Listener {
        void addConcept(InputCons cons);
        void deleteConcept(InputCons cons);
        void editFeatures(InputCons cons);
        void toggleOptional(InputCons cons, boolean optional);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public InputDetailsPanel() {
        optional = new JCheckBox("Optional");
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
        
        optional.addActionListener(new SetOptional());
        addConcept.addActionListener(new AddConcept());
        deleteConcept.addActionListener(new DeleteConcept());
        editFeatures.addActionListener(new EditFeatures());
        
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
        
        String text = RuleUtils.getFeaturesText(cons.getFeatureInputs());
        featuresArea.setText(text);
        
        optional.setSelected(cons.isOptional());
    }
    
    private class AddConcept implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.addConcept(cons);
            }
        }
        
    }
    
    private class DeleteConcept implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.deleteConcept(cons);
            }
        }
        
    }
    
    private class EditFeatures implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.editFeatures(cons);
            }
        }
        
    }
    
    private class SetOptional implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.toggleOptional(cons, optional.isSelected());
            }
        }
        
    }
}
