package lexicon.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import lexicon.model.Entry;
import grammar.model.Feature;
import grammar.view.FeatureCombobox;

@SuppressWarnings("serial")
public class EntryFeatureCombobox extends FeatureCombobox {
    private Entry entry;
    private List<Listener> listeners = new ArrayList<Listener>();
    
    
    public interface Listener {
        public abstract void featureValueChanged(Entry entry, Feature feature, String value);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public EntryFeatureCombobox(Entry entry, Feature feature) {
        super(feature);
        this.entry = entry;
        addItemListener(new EntryComboListener());
    }
    
    private class EntryComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                EntryFeatureCombobox comboBox = (EntryFeatureCombobox) ev.getSource();
                for (Listener listener : listeners) {
                    listener.featureValueChanged(entry, comboBox.getFeature(), comboBox.getValue());
                }
            }
        }
    }
}
