package rule.generic;

import grammar.model.Feature;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class FeatureCheckbox extends JCheckBox {
    private Feature feature;
    
    public FeatureCheckbox(Feature feature) {
        super(feature.getValue());
        this.feature = feature;    
    }
    
    Feature getFeature() {
        return feature;
    }
    
    String getValue() {
        return feature.getValue();
    }
    
}
