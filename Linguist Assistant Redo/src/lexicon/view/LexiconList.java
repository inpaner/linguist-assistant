package lexicon.view;

import grammar.model.Constituent;
import grammar.model.Feature;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;

import lexicon.model.Entry;
import lexicon.model.Form;
import lexicon.model.Language;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;

import commons.main.MainFrame;

public class LexiconList extends JPanel {
    
    private JTextField searchField;
    private JComboBox<Constituent> constituentBox;
    private JComboBox<Language> languageBox;
    private LexiconStemsModel stemsModel;
    private LexiconFormsModel formsModel;
    private JXTable table;
    private LexiconFeaturesModel featuresModel;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        LexiconList list = new LexiconList();
        frame.setPanel(list);
        list.refresh();
    }
    
    public LexiconList() {
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new SearchListener());
        
        Vector<Constituent> constituents = new Vector<>(Constituent.getAll());
        constituentBox = new JComboBox<>(constituents);
        constituentBox.addItemListener(new ComboListener());
        
        Vector<Language> languages = new Vector<>(Language.getAll());
        languageBox = new JComboBox<>(languages);
        languageBox.addItemListener(new ComboListener());
        
        stemsModel = new LexiconStemsModel();
        
        table = new JXTable();
        table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(400, 300));
        
        setLayout(new MigLayout());
        add(searchLabel, "span, split");
        add(searchField, "wrap");
        add(constituentBox, "span, split");
        add(languageBox, "gap para, wrap");
        add(scrollpane, "");
    }
    
    private void refresh() {
        String substring = searchField.getText();
        Language language = languageBox.getItemAt(languageBox.getSelectedIndex());
        Constituent constituent = constituentBox.getItemAt(constituentBox.getSelectedIndex());
        
        List<Entry> entries = Entry.getAll(substring, language, constituent);
        stemsModel.update(entries);
        stemsModel.fireTableDataChanged();
        
        formsModel = new LexiconFormsModel(entries);
        featuresModel = new LexiconFeaturesModel(entries);
        if (table != null)
            table.setModel(featuresModel);
        
        
        formsModel.fireTableDataChanged();
        featuresModel.fireTableDataChanged();
        
    }
    
    private class LexiconStemsModel extends AbstractTableModel {
        List<Entry> entries;
        
        private LexiconStemsModel() {
            entries = new ArrayList<>();
        }
        
        public void update(List<Entry> entries) {
            this.entries = entries;
        }
        
        public int getColumnCount() {
            return 4;
        }
        
        public int getRowCount() {
            return entries.size();
        }
        
        public String getColumnName(int columnIndex) {
            String result;
            switch (columnIndex) {
            case 0: result = "Stem";
                    break;
            case 1: result = "Gloss";
                    break;
            case 2: result = "Comment";
                    break;
            case 3: result = "Sample Sentence";
                    break;
            default: result = ""; 
            }
            return result;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            Object result;
            switch (columnIndex) {
                case 0: result = entry.getStem();
                        break;
                case 1: result = entry.getGloss();
                        break;
                case 2: result = entry.getComment();
                        break;
                case 3: result = entry.getSampleSentence();
                        break;
                default: result = "";
            }
            
            return result;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class LexiconFormsModel extends AbstractTableModel {
        List<Entry> entries;
        List<List<Form>> allForms;
        int columns;
        
        private LexiconFormsModel(List<Entry> entries) {
            this.entries = entries;
            allForms = new ArrayList<>();
            for (Entry entry : entries) {
                List<Form> forms = entry.getForms(); 
                allForms.add(forms);
            }
            columns = 2;
            if (allForms.size() > 0) {
                List<Form> firstForms = allForms.get(0);
                columns += firstForms.size();
            }
        }
        
        public int getColumnCount() {
            return columns;
        }
        
        public int getRowCount() {
            return entries.size();
        }
        
        public String getColumnName(int columnIndex) {
            String result = "";
            switch (columnIndex) {
            case 0: result = "Stem";
                    break;
            case 1: result = "Gloss";
                    break;
            default: columnIndex -= 2;
                     if (allForms.size() > 0) {
                         List<Form> firstForms = allForms.get(0);
                         result = firstForms.get(columnIndex).getName();
                     } 
            }
            
            return result;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            Object result = "";
            switch (columnIndex) {
                case 0: result = entry.getStem();
                        break;
                case 1: result = entry.getGloss();
                        break;
                default: columnIndex -= 2; 
                         if (allForms.size() > 0) {
                             List<Form> firstForms = allForms.get(rowIndex);
                             result = firstForms.get(columnIndex).getValue();
                         }
            }
            return result;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class LexiconFeaturesModel extends AbstractTableModel {
        List<Entry> entries;
        List<List<Feature>> allFeatures;
        int columns;
        
        private LexiconFeaturesModel(List<Entry> entries) {
            this.entries = entries;
            allFeatures = new ArrayList<>();
            for (Entry entry : entries) {
                List<Feature> features = entry.getFeatures(); 
                allFeatures.add(features);
            }
            
            columns = 2;
            if (allFeatures.size() > 0) {
                List<Feature> temp = allFeatures.get(0);
                columns += temp.size();
            }
        }
        
        public int getColumnCount() {
            return columns;
        }
        
        public int getRowCount() {
            return entries.size();
        }
        
        public String getColumnName(int columnIndex) {
            String result = "";
            switch (columnIndex) {
            case 0: result = "Stem";
                    break;
            case 1: result = "Gloss";
                    break;
            default: columnIndex -= 2;
                     if (allFeatures.size() > 0) {
                         List<Feature> temp = allFeatures.get(0);
                         result = temp.get(columnIndex).getName();
                     } 
            }
            
            return result;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            Object result = "";
            switch (columnIndex) {
                case 0: result = entry.getStem();
                        break;
                case 1: result = entry.getGloss();
                        break;
                default: columnIndex -= 2; 
                         if (allFeatures.size() > 0) {
                             List<Feature> temp = allFeatures.get(rowIndex);
                             result = temp.get(columnIndex).getValue();
                         }
            }
            return result;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    
    
    private class SearchListener implements DocumentListener {
        public void changedUpdate(DocumentEvent ev) {}

        @Override
        public void insertUpdate(DocumentEvent ev) {
            refresh();
        }
        
        @Override
        public void removeUpdate(DocumentEvent ev) {
            refresh();
        }        
    }
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                refresh();
                
            }
        }
    }
}
