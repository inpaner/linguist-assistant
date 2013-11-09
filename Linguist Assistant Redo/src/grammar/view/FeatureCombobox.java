package grammar.view;

import grammar.model.Feature;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;


@SuppressWarnings("serial")
public class FeatureCombobox extends JComboBox<String> {
    private Feature feature;
    private List<Listener> listeners = new ArrayList<Listener>();
    
    public interface Listener {
        public abstract void featureValueChanged(Feature feature, String value);
    }
    
    public FeatureCombobox(Feature feature) {
        super(new Vector<String>(feature.getPossibleValues()));
        this.feature = feature;
        setSelectedItem(feature.getValue());
        addItemListener(new ComboListener());
    }
    
    protected Feature getFeature() {
        return feature;
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    protected String getValue() {
        return (String) getSelectedItem();
    }
    
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                FeatureCombobox comboBox = (FeatureCombobox) ev.getSource();
                for (Listener listener : listeners) {
                    listener.featureValueChanged(comboBox.getFeature(), comboBox.getValue());
                }
            }
        }
    }
}
