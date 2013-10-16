package view;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Constituent;
import model.Feature;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AddConstituentPanel extends JPanel {
    private List<AddConstituentListener> listeners;
    private JLabel constituentLabel;
    private FeatureValuesPanel featureValuesPanel;
    private JButton ok;
    private JButton cancel;
    private Constituent constituent;
    
    public AddConstituentPanel(Constituent constituent) {
        this.constituent = constituent;
        listeners = new ArrayList<>();
        initComponents();
        addComponents();
    }
    
    private void initComponents() {        
        setLayout(new MigLayout("wrap 1"));
        
        constituentLabel = new JLabel("Constituent: " + constituent.getLabel());
        
        featureValuesPanel = new FeatureValuesPanel();
        featureValuesPanel.addFeatureValuesListener(new ActualFeatureValuesListener());
        updateFeatureValuesPanel();
        
        Dimension buttonSize = new Dimension(70, 0);
        ok = new JButton("OK");
        ok.setPreferredSize(buttonSize);
        ok.addMouseListener(new ClickedOk());
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(buttonSize);
        cancel.addMouseListener(new ClickedCancel());
    }
    
    private void addComponents() {
        add(constituentLabel);
        add(featureValuesPanel, "span");
        
        add(ok, "span, split 2, right");
        add(cancel);
    }
    
    
    // not sure if this should be stuck in view
    private class ActualFeatureValuesListener implements FeatureValuesListener {
        @Override
        public void featureValueChanged(Feature feature, String newValue) {
            Constituent parent = feature.getParent();
            parent.updateFeature(feature, newValue);
        }
    }
    
    public void addListener(AddConstituentListener listener) {
        listeners.add(listener);
    }
    
    
    private void updateFeatureValuesPanel() {
        featureValuesPanel.setConstituent(constituent);
    }
    
    private class ClickedOk implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (AddConstituentListener listener : listeners) {
                listener.clickedOk(constituent);
            }
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}
    }
    
    private class ClickedCancel implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (AddConstituentListener listener : listeners) {
                listener.clickedCancel();
            }
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}
    }
    
    public interface Listener {
        public abstract void clickedOk(Constituent constituent);
        public abstract void clickedCancel();
    }
}
