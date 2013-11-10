package rule.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

/*
 * http://www.coderanch.com/t/343795/GUI/java/Check-Box-JTable-header
 */
public class FeatureSelectorTable extends JPanel {
    private List<Feature> features;
    private List<List<FeatureCheckbox>> checkboxesList = new ArrayList<>();
    private Map<Feature, FeatureCheckbox> checkboxMap = new HashMap<>();
    private JButton ok;
    private JButton cancel;
    
    public static void main(String[] args) {    
        MainFrame frame = new MainFrame();
        Category category = Category.getByName("Noun");
        FeatureSelectorTable panel = new FeatureSelectorTable(category);
        frame.setPanel(panel);
        
        Feature feature = Feature.getEmpty(category);
        feature.setName("number");
        feature.setValue("dual");
        
        List<Feature> test = new ArrayList<>();
        test.add(feature);
        
        panel.setSelected(test);
    }
    
    public FeatureSelectorTable(Category category) {
        
        /*
         * Checkbox inner panel
         */
        features = category.getFeatures();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new MigLayout("flowy"));
        
        for (Feature feature : features) {
            JLabel featureLabel = new JLabel(feature.getName());
            List<FeatureCheckbox> checkboxes = new ArrayList<>();
            innerPanel.add(featureLabel);
            innerPanel.add(new JSeparator(), "growx");
            
            for (String value : feature.getPossibleValues()) {

                Feature copy = Feature.copy(feature);
                copy.setValue(value);
                FeatureCheckbox checkbox = new FeatureCheckbox(copy); 
                innerPanel.add(checkbox);
                checkboxes.add(checkbox);
                checkboxMap.put(copy, checkbox);
            }
            checkboxesList.add(checkboxes);
            innerPanel.add(new JLabel(), "wrap, gap 70");
        }   
        JScrollPane scrollpane = new JScrollPane(innerPanel);

        /*
         * Buttons
         */
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(new CancelListener());
        
        setLayout(new MigLayout());
        add(scrollpane, "wrap");
        add(ok, "span, split, right");
        add(cancel);
    }
    
    public List<List<Feature>> getSelected() {
        List<List<Feature>> result = new ArrayList<>();
        
        for (List<FeatureCheckbox> checkboxes : checkboxesList) {
            List<Feature> features = new ArrayList<>();
            for (FeatureCheckbox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    System.out.println(checkbox.getFeature() + " " + checkbox.getValue());
                    features.add(checkbox.getFeature());
                }
            }
            if (!features.isEmpty()) {
                result.add(features);
            }
        }
        
        return result;
    }
    
    
    public void setSelected(List<Feature> features) {
        for (Feature basis : features) {
            FeatureCheckbox checkbox = checkboxMap.get(basis);
            if (checkbox != null) {
                checkbox.setSelected(true);
            }
        }
    }
    
    private class OkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            getSelected();
        }
        
    }
    
    private class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            
        }
        
    }
}
