package grammar.view;

import grammar.model.Feature;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;


public class FeatureComboBox extends JComboBox<String> {
    private Feature feature;
    private List<Listener> listeners;
    
    public interface Listener {
        public abstract void featureValueChanged(Feature feature, String value);
    }
    
    public FeatureComboBox(Feature feature) {
        super(new Vector<String>(feature.getPossibleValues()));
        listeners = new ArrayList<Listener>();
        this.feature = feature;
        setSelectedItem(feature.getValue());
        addItemListener(new ComboListener());
    }
    
    private Feature getFeature() {
        return feature;
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    private String getValue() {
        return (String) getSelectedItem();
    }
    
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                FeatureComboBox comboBox = (FeatureComboBox) ev.getSource();
                for (Listener listener : listeners) {
                    listener.featureValueChanged(comboBox.getFeature(), comboBox.getValue());
                }
            }
        }
    }
}
