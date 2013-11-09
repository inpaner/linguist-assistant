package ontology.view;

import grammar.model.Category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AddConceptDialog extends JDialog {
    private List<Listener> listeners = new ArrayList<>();
    private JComboBox<Category> constituentBox;
    private JTextField stemField;
    private JTextField glossField;
    private JButton ok;
    private JButton cancel;
    
    public class Event {
        private String stem;
        private String gloss;
        private Category category;
        private Event() {
            stem = stemField.getText();
            gloss = glossField.getText();
            category = (Category) constituentBox.getSelectedItem();
        }
        
        public String getStem() {
            return stem;
        }
        
        public String getGloss() {
            return gloss;
        }
        
        public Category getCategory() {
            return category;
        }
    }
    
    public interface Listener {
        public abstract void add(Event event);
        public abstract void cancel();
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public AddConceptDialog(Listener listener) {
        addListener(listener);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(200, 150);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        Vector<Category> categories = new Vector<>(Category.getAll());
        constituentBox = new JComboBox<>(categories);
        
        JPanel panel = new JPanel();
        JLabel stemLabel = new JLabel("Stem: ");
        stemField = new JTextField(20);
        JLabel glossLabel = new JLabel("Gloss: ");
        glossField = new JTextField(20);
        
        ok = new JButton("Add");
        cancel = new JButton("Cancel");
        ok.addActionListener(new AddListener());
        cancel.addActionListener(new CancelListener());
        
        panel.setLayout(new MigLayout("wrap 2"));
        panel.add(constituentBox, "span, split, wrap");
        panel.add(stemLabel);
        panel.add(stemField);
        panel.add(glossLabel);
        panel.add(glossField);
        panel.add(ok);
        setContentPane(panel);
        setVisible(true);
    }
    
    private class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Event event = new Event();
            for (Listener listener : listeners) {
                listener.add(event);
            }
            dispose();
        }
    }
    
    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.cancel();
            }
            dispose();
        }
    }

}
