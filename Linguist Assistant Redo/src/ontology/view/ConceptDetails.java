package ontology.view;

import grammar.model.Category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import lexicon.model.Entry;
import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;
import ontology.model.Tag;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.plaf.misc.GlossyTaskPaneUI;

import commons.main.MainFrame;

public class ConceptDetails extends JPanel {
    private final int FIELD_WIDTH = 20;
    private final int AREA_HEIGHT = 3;
    private JTextField stemField;
    private JTextField senseField;
    private JTextArea glossArea;
    private TagTableModel tagModel;
    private JXTable tagTable;
    private LexiconTableModel mappingsModel;
    private JXTable mappingsTable;
    private Concept concept;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public abstract void addTag(Concept concept);
        public abstract void delTag(Concept concept, Tag tag);
        public abstract void addMapping(Concept concept);
        public abstract void delMapping(Concept concept, Entry entry);
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        ConceptDetails panel = new ConceptDetails();
        frame.setPanel(panel);
        Category con = Category.getByName("Verb");
        Concept c = Concept.getInstance("run", "A", con);
        panel.refresh(c);
    }
    
    public ConceptDetails() {
        // Details
        JLabel detailsLabel = new JLabel("Details");
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new MigLayout("wrap 2"));
        
        JLabel stem = new JLabel("Stem: ");
        stemField = new JTextField(FIELD_WIDTH);
        
        JLabel sense = new JLabel("Sense: ");
        senseField = new JTextField(3);
        
        JLabel gloss = new JLabel("Gloss: ");
        glossArea = new JTextArea(AREA_HEIGHT, FIELD_WIDTH);
        glossArea.setWrapStyleWord(true);
        glossArea.setLineWrap(true);
        JScrollPane glossPane = new JScrollPane(glossArea);
        
        detailsPanel.add(stem);
        detailsPanel.add(stemField);
        detailsPanel.add(sense);
        detailsPanel.add(senseField);
        detailsPanel.add(gloss);
        detailsPanel.add(glossPane);
        
        // Tags
        JLabel tagsLabel = new JLabel("Tags");
        tagModel = new TagTableModel();
        tagTable = new JXTable(tagModel);
        tagTable.setVisibleRowCount(3);
        JScrollPane tagPane = new JScrollPane(tagTable);
        JButton addTag = new JButton("+");
        JButton delTag = new JButton("�");
        Box tagBox = new Box(BoxLayout.Y_AXIS);
        tagBox.add(addTag);
        tagBox.add(delTag);
        addTag.addActionListener(new AddTag());
        delTag.addActionListener(new DelTag());
        
        // Lexicon Mappings
        JLabel mappingsLabel = new JLabel("Mappings");
        mappingsModel = new LexiconTableModel();
        mappingsTable = new JXTable(mappingsModel);
        mappingsTable.setVisibleRowCount(3);
        JScrollPane mappingsPane = new JScrollPane(mappingsTable);
        JButton addMapping = new JButton("+");
        JButton delMapping = new JButton("�");
        Box mappingBox = new Box(BoxLayout.Y_AXIS);
        mappingBox.add(addMapping);
        mappingBox.add(delMapping);
        addMapping.addActionListener(new AddMapping());
        delMapping.addActionListener(new DelMapping());
        
        setLayout(new MigLayout("wrap 1"));
        add(detailsLabel);
        add(detailsPanel);
        
        add(new JSeparator(), "gapleft rel, growx, wrap");
        add(tagsLabel);
        add(tagPane, "span, split 2");
        add(tagBox);
        
        add(new JSeparator(), "gapleft rel, growx");
        add(mappingsLabel);
        add(mappingsPane, "span, split 2");
        add(mappingBox);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public void refresh(Concept concept) {
        if (concept == null)
            return;
        
        this.concept = concept;
        stemField.setText(concept.getStem());
        senseField.setText(concept.getSense());
        glossArea.setText(concept.getGloss());
        
        tagModel.updateTags(concept.getTags());
        tagModel.fireTableDataChanged();
        
        mappingsModel.updateEntries(concept.getMappings());
        mappingsModel.fireTableDataChanged();
    }
    
    public Tag getSelectedTag() {
        int index = tagTable.getSelectedRow();
        return tagModel.getTag(index);
    }
    
    public Entry getSelectedEntry() {
        int index = mappingsTable.getSelectedRow();
        return mappingsModel.getEntry(index);
    }
    
    
    @SuppressWarnings("serial")
    private class TagTableModel extends AbstractTableModel {
        List<Tag> tags;
        
        private TagTableModel() {
            tags = new ArrayList<>();
        }
    
        public int getColumnCount() {
            return 1;
        }
    
        public int getRowCount() {
            return tags.size();
        }
        
        public void updateTags(List<Tag> tags) {
            this.tags = tags;
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            
            switch (columnIndex) {
            case 0: colName = "";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Tag tag = tags.get(rowIndex);
            Object value;
            switch (columnIndex) {
            case 0: value = tag;
                    break;
            default: value = "";
            }
            
            return value;
        }
        
        public Tag getTag(int index) {
            if (index < 0 || index >= tags.size())
                return null;
            return tags.get(index);
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    @SuppressWarnings("serial")
    private class LexiconTableModel extends AbstractTableModel {
        List<Entry> entries;
        
        private LexiconTableModel() {
            entries = new ArrayList<>();
        }
    
        public int getColumnCount() {
            return 2;
        }
    
        public int getRowCount() {
            return entries.size();
        }
        
        public void updateEntries(List<Entry> entries) {
            this.entries = entries;
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            
            switch (columnIndex) {
            case 0: colName = "Entry";
                    break;
            case 1: colName = "Language";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            Object value;
            switch (columnIndex) {
                case 0: value = entry;
                        break;
                case 1: value = entry.getLanguage();
                        break;
                
                default: value = "";
            }
            
            return value;
        }
        
        public Entry getEntry(int index) {
            if (index < 0 || index >= entries.size())
                return null;
            return entries.get(index);
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    private class AddTag implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (concept == null)
                return;
            
            
            for (Listener listener : listeners) {
                listener.addTag(concept);
            }
        }   
    }
    
    private class DelTag implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tag selectedTag = getSelectedTag();
            
            if (concept == null || selectedTag == null)
                return;
            
            for (Listener listener : listeners) {
                listener.delTag(concept, selectedTag);
            }            
        }   
    }
    
    private class AddMapping implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (concept == null)
                return;
            
            for (Listener listener : listeners) {
                listener.addMapping(concept);
            }            
        }   
    }
    
    private class DelMapping implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Entry selectedEntry = getSelectedEntry();
            if (concept == null || selectedEntry == null)
                return;
            
            for (Listener listener : listeners) {
                listener.delMapping(concept, getSelectedEntry());
            }            
        }   
    }
}
