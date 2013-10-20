package view;

import grammar.model.Constituent;
import grammar.model.Feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import commons.view.MainFrame;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
// OMG WORST CLASS EVER
public class GrammarEditorPanel extends JPanel {
    
    private JComboBox<Constituent> constituents;
    private JLabel constituentLabel;
    private Constituent selectedConstituent;
    private Feature selectedFeature;
    private JTable featuresTable;
    private JTable valuesTable;
    private JScrollPane featuresPane;
    private JScrollPane valuesPane;
    private FeatureTableModel featureModel;
    private ValueTableModel valuesModel;
        
    private JButton addFeature;
    private JButton editFeature;
    private JButton delFeature;
    private JButton addValue;
    private JButton editValue;
    private JButton delValue;
    
    public GrammarEditorPanel() {
        initComponents();
        addComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("wrap 2"));
        constituentLabel = new JLabel("Constituent: ");
        Vector<Constituent> vector = new Vector<>(Constituent.getAllConstituents());
        
        constituents = new JComboBox<>(vector);
        selectedConstituent = constituents.getItemAt(0);
        constituents.addItemListener(new ComboListener());
        
        featureModel = new FeatureTableModel();
        featuresTable = new JTable(featureModel);
        featuresTable.addMouseListener(new FeatureTableListener());
        featuresPane = new JScrollPane(featuresTable);
        if(selectedConstituent.hasFeatures())
        selectedFeature = selectedConstituent.getFeatures().get(0);
        
        valuesModel = new ValueTableModel();
        valuesTable = new JTable(valuesModel);
        valuesPane = new JScrollPane(valuesTable);
        
        addFeature = new JButton("Add");
        editFeature = new JButton("Edit");
        delFeature = new JButton("Delete");
        addValue = new JButton("Add");
        editValue = new JButton("Edit");
        delValue = new JButton("Delete");
        
        // Y U DO DIS
        addFeature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                 TextDialog dialog = new TextDialog("Feature name");
                 String name = dialog.getText();
                 System.out.println(name);
                 selectedConstituent.addNewFeature(name);
            }
        });
        editFeature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                
            }
        });
        delFeature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                
            }
        });
        addValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                
            }
        });
        editValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                
            }
        });
        delValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                
            }
        });
    }
    
    private void addComponents() {
        add(constituentLabel, "span, split");
        add(constituents, "wrap");
        add(featuresPane);
        add(valuesPane);
        add(addFeature, "center, flowx");
        add(addValue, "center, flowx");
        add(editFeature, "cell 0 2");
        add(delFeature, "cell 0 2");
        add(editValue, "cell 1 2");
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
        	if(selectedConstituent.hasFeatures())
            return selectedConstituent.getFeatures().size();
        	else return 0;
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
            Feature feature = selectedConstituent.getFeatures().get(rowIndex); 
            return feature;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    private class ValueTableModel extends AbstractTableModel {
        public ValueTableModel() {}
    
        public int getColumnCount() {
            return 1;
        }
    
        public int getRowCount() {
        	if(selectedFeature!=null)
            return selectedFeature.getPossibleValues().size();
        	else return 0;
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Value";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            List<String> possibleValues = selectedFeature.getPossibleValues();
            
            return possibleValues.get(rowIndex);
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                int index = constituents.getSelectedIndex();
                selectedConstituent = constituents.getItemAt(index);
                featureModel.fireTableDataChanged();
                try {
                    selectedFeature = selectedConstituent.getFeatures().get(0);
                    valuesModel.fireTableDataChanged();
                }
                catch (IndexOutOfBoundsException ex) {
                    // TODO clear valuesmodel
                }
            }
        }
    }
    
    private class FeatureTableListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = featuresTable.getSelectedRow();
            int col = featuresTable.getSelectedColumn();
            selectedFeature = (Feature) featuresTable.getValueAt(row, col);
            valuesModel.fireTableDataChanged();
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
    
}
