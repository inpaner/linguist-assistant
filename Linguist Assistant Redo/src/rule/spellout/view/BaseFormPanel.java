package rule.spellout.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;
import lexicon.model.Form;

public class BaseFormPanel extends JPanel {
    public List<Listener> listeners = new ArrayList<>();
    private JComboBox<Form> forms;    
    
    public interface Listener {
        void selectedFeatures();
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Category c = Category.getByName("Noun");
        JPanel out = new JPanel();
        HeaderPanel header = new HeaderPanel(c);
        ModificationPanel mod = new ModificationPanel();
        BaseFormPanel base = new BaseFormPanel(c);
        
        out.setLayout(new MigLayout("wrap 1"));
        
        out.add(header);
        out.add(mod);
        out.add(base);
        frame.setPanel(out);
    }
    
    public BaseFormPanel(Category category) {
        JLabel baseLabel = new JLabel("Base Form:");
        Vector<Form> formsVector = new Vector<>(category.getForms());
        forms = new JComboBox<>(formsVector);
        
        JButton features = new JButton("Features");
        JTextArea featuresArea = new JTextArea(3, 20);
        featuresArea.setEditable(false);
        JScrollPane featuresScroll = new JScrollPane(featuresArea);
        
        features.addActionListener(new FeaturesListener());
        
        setLayout(new MigLayout("wrap 2"));
        add(baseLabel);
        add(forms);
        add(features);
        add(featuresScroll);
    }
    
    public Form getForm() {
        return forms.getItemAt(forms.getSelectedIndex());
    }
    
    public void setFeatures(List<Feature> features) {
        
    }
    
    private class FeaturesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.selectedFeatures();
            }
        }
        
    }
}
