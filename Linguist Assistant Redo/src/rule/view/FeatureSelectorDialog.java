package rule.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FeatureSelectorDialog extends JDialog {
    private JTextField title;
    private FeatureSelectorTable table;
    private JButton ok;
    private JButton cancel;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public void select(String title, List<List<Feature>> features);
        public void cancel();
    }
    
    public FeatureSelectorDialog(Listener listener, Category category) {
        listeners.add(listener);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        
        JLabel titleLabel = new JLabel("Title");
        title = new JTextField(20);
        table = new FeatureSelectorTable(category);
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(new CancelListener());
        
        JPanel content = new JPanel();
        content.setLayout(new MigLayout());
        content.add(titleLabel, "span, split");
        content.add(title, "wrap");
        content.add(table, "wrap");
        content.add(ok, "span, split, right");
        content.add(cancel);
        
        setContentPane(content);
        setVisible(true);
    }
    
    public FeatureSelectorDialog(Listener listener, Category category, 
            String titleString, List<Feature> features) {
        this(listener, category);
        this.title.setText(titleString);
        table.setSelected(features);
    }
    
    private class OkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            for (Listener listener : listeners) {
                listener.select(title.getText(), table.getSelected());
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
