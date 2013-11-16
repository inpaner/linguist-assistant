package rule.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class FeatureSelectorTable extends JPanel {
    private List<Feature> features;
    private List<List<FeatureCheckbox>> checkboxesList = new ArrayList<>();
    private Map<Feature, FeatureCheckbox> checkboxMap = new HashMap<>();

    
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


        setLayout(new MigLayout());
        add(scrollpane);
        
    }
    
    public List<List<Feature>> getSelected() {
        List<List<Feature>> result = new ArrayList<>();
        
        for (List<FeatureCheckbox> checkboxes : checkboxesList) {
            List<Feature> features = new ArrayList<>();
            for (FeatureCheckbox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
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

}
