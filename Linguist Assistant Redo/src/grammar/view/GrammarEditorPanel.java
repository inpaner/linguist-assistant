package grammar.view;

import grammar.model.Constituent;
import grammar.model.Feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import lexicon.model.Language;
import net.miginfocom.swing.MigLayout;

import commons.main.MainFrame;

@SuppressWarnings("serial")
public class GrammarEditorPanel extends JPanel {
    private List<Listener> listeners = new ArrayList<Listener>();
    private JComboBox<Language> languages;
    private JComboBox<Constituent> constituents;
    private JTable featuresTable;
    private JTable valuesTable;
    private FeatureTableModel featureModel;
    private ValueTableModel valuesModel;
    
    public interface Listener {
        public abstract void addFeature(Event event);
        public abstract void editFeature(Event event);
        public abstract void deleteFeature(Event event);
        public abstract void addValue(Event event);
        public abstract void editValue(Event event);
        public abstract void deleteValue(Event event);
    }
    
    public class Event {
        private Language language;
        private Constituent constituent;
        private Feature feature;
        private String replacement = "";
        private int valueIndex = -1;
        
        private Event() {
            constituent = selectedConstituent();
            feature = selectedFeature();
        }
        
        public Language getLanguage() {return language;}
        
        public Constituent getConstituent() {return constituent;}
        
        public Feature getFeature() {return feature;}
        
        public String replacement() {return replacement;}
        
        public int getValueIndex() {return valueIndex;}
    }
    
    public GrammarEditorPanel() {
        JLabel languageLabel = new JLabel("Language: ");
        Vector<Language> langaugeList = new Vector<>(Language.getAll());
        JLabel constituentLabel = new JLabel("Constituent: ");
        Vector<Constituent> constituentList = new Vector<>(Constituent.getAll());
        
        constituents = new JComboBox<>(constituentList);
        constituents.addItemListener(new ConstiutentComboListener());
        
        featureModel = new FeatureTableModel();
        featuresTable = new JTable(featureModel);
        featuresTable.addMouseListener(new FeatureTableListener());
        JScrollPane featuresPane = new JScrollPane(featuresTable);
        
        valuesModel = new ValueTableModel();
        valuesTable = new JTable(valuesModel);
        JScrollPane valuesPane = new JScrollPane(valuesTable);
        
        JButton addFeature = new JButton("Add");
        JButton delFeature = new JButton("Delete");
        JButton addValue = new JButton("Add");
        JButton delValue = new JButton("Delete");
        
        addFeature.addActionListener(new AddFeatureListener());
        delFeature.addActionListener(new DeleteFeatureListener());
        addValue.addActionListener(new AddValueListener());
        delValue.addActionListener(new DeleteValueListener());
        
        setLayout(new MigLayout("wrap 2"));
        add(constituentLabel, "span, split");
        add(constituents, "wrap");
        add(featuresPane);
        add(valuesPane);
        add(addFeature, "center, flowx");
        add(addValue, "center, flowx");
        add(delFeature, "cell 0 2");
        add(delValue, "cell 1 2");
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        GrammarEditorPanel panel = new GrammarEditorPanel();
        frame.setPanel(panel);
    }
    
    private class FeatureTableModel extends AbstractTableModel {
        public FeatureTableModel() {}
        
        public int getColumnCount() {
            return 1;
        }
    
        public int getRowCount() {
            try {
                Constituent selected = selectedConstituent();
                return selected.getFeatures().size();
            }
            catch (IndexOutOfBoundsException ex) {
                return 0;
            }
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Feature";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {

            try {  
                Constituent selected = selectedConstituent();
                Feature feature = selected.getFeatures().get(rowIndex);
                return feature;
            }
            catch (IndexOutOfBoundsException ex) {

            }
                
            return null;
        }
        
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            Event event = new Event();
            event.replacement = (String) value;
            for (Listener listener : listeners) {
                listener.editFeature(event);
            }
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class ValueTableModel extends AbstractTableModel {
        public ValueTableModel() {}

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public int getRowCount() {
            Feature selected = selectedFeature();
        	if (selected != null)
        	    return selected.getPossibleValues().size();
        	else return 0;
        }

        @Override
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Value";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Feature selected = selectedFeature();
            
            List<String> possibleValues = selected.getPossibleValues();
            
            return possibleValues.get(rowIndex);
        }
        
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            Event event = new Event();
            event.replacement = (String) value;
            event.valueIndex = rowIndex;
            for (Listener listener : listeners) {
                listener.editValue(event);
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class ConstiutentComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                featureModel.fireTableDataChanged();
                valuesModel.fireTableDataChanged();
            }
        }
    }
    
    private Constituent selectedConstituent() {
        int index = constituents.getSelectedIndex();
        return constituents.getItemAt(index);
    }
    
    private Feature selectedFeature() {
        int row = featuresTable.getSelectedRow();
        int col = featuresTable.getSelectedColumn();
        return (Feature) featuresTable.getValueAt(row, col);
    }
    
    private String selectedValue() {
        int row = valuesTable.getSelectedRow();
        int col = valuesTable.getSelectedColumn();
        return (String) valuesTable.getValueAt(row, col);
    }
    
    private class FeatureTableListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent ev) {
            valuesModel.fireTableDataChanged();
        }

        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}

    }
    
    private class AddFeatureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // TODO Auto-generated method stub
            
        }
    }
    
    private class DeleteFeatureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (selectedFeature() == null) {
                return;
            }
            for (Listener listener : listeners) {
                listener.deleteFeature(new Event());
            }
        }
    }
    
    private class AddValueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // TODO Auto-generated method stub
            
        }
    }
    
    private class DeleteValueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            int index = valuesTable.getSelectedRow();
            
            // None selected
            if (index == -1) {
                return;
            }
            
            Event event = new Event();
            event.valueIndex = index;
            for (Listener listener : listeners) {
                listener.deleteValue(event);
            }
        }
    }
}
