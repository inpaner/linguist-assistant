package rule.spellout;

import grammar.model.Category;
import grammar.model.Feature;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import rule.generic.FeatureSelectorDialog.Listener;
import rule.model.RuleSet;

public class RuleMakerDialog extends JDialog {
    private SpelloutTableUi table;
    private JButton ok;
    private JButton cancel;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public void select(RuleSet ruleSet);
        public void cancel();
    }
    
    public RuleMakerDialog(Listener listener, Category category) {
        listeners.add(listener);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        
        table = new SpelloutTableUi(category);
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(new CancelListener());
        
        JPanel content = new JPanel();
        content.setLayout(new MigLayout());
        content.add(table, "wrap");
        content.add(ok, "span, split, right");
        content.add(cancel);
        
        setContentPane(content);
        setVisible(true);
    }
    
    
    private class OkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            for (Listener listener : listeners) {
                listener.select(table.getRules());
            }
            dispose();
        }   
    }
    
    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            for (Listener listener : listeners) {
                listener.cancel();
            }
            dispose();
        }
        
    }
}
