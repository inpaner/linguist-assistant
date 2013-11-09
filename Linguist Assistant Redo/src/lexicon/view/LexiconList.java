package lexicon.view;

import grammar.model.Category;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lexicon.model.Entry;
import lexicon.model.Language;
import lexicon.view.table.FeatureStrategy;
import lexicon.view.table.FormStrategy;
import lexicon.view.table.StemStrategy;
import lexicon.view.table.TableStrategy;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;

import commons.main.MainFrame;

@SuppressWarnings("serial")
public class LexiconList extends JPanel {
    private List<Entry> entries;
    private List<Listener> listeners = new ArrayList<>();
    private JTextField searchField;
    private JComboBox<Category> categoryBox;
    private JComboBox<Language> languageBox;
    private JXTable table;
    private TableStrategy strategy;
    
    public interface Listener {
        public abstract void selectedEntry(Entry entry);
    }
    
    public LexiconList(Category category) {
        this();
        categoryBox.setSelectedItem(category);
        refresh();
    }
    
    public LexiconList() {
        strategy = new StemStrategy();
        
        // Init UI components
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new SearchListener());
        
        Vector<Category> categories = new Vector<>(Category.getAll());
        categoryBox = new JComboBox<>(categories);
        categoryBox.addItemListener(new ComboListener());
        
        Vector<Language> languages = new Vector<>(Language.getAll());
        languageBox = new JComboBox<>(languages);
        languageBox.addItemListener(new ComboListener());
        
        JButton stemButton = new JButton("Stems");
        JButton formButton = new JButton("Forms");
        JButton featureButton = new JButton("Features");
        stemButton.addActionListener(new StemButtonListener());
        formButton.addActionListener(new FormButtonListener());
        featureButton.addActionListener(new FeatureButtonListener());
        
        table = new JXTable();
        table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListListener());
        
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(400, 300));
        
        // Add UI components
        setLayout(new MigLayout());
        add(searchLabel, "span, split");
        add(searchField, "wrap");
        
        add(categoryBox, "span, split");
        add(languageBox, "gap para, wrap");
        
        add(stemButton, "span, split");
        add(formButton);
        add(featureButton, "wrap");
        add(scrollpane);
        
        refresh();
    }
    
    private void refresh() {
        String substring = searchField.getText();
        Language language = languageBox.getItemAt(languageBox.getSelectedIndex());
        Category category = categoryBox.getItemAt(categoryBox.getSelectedIndex());
        
        entries = Entry.getAll(substring, language, category);
        strategy.update(table, entries);   
    }
    
    public Entry getSelected() {
        int index = table.getSelectedRow();
        if (index == -1)
            return null;
        return entries.get(index); 
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
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
    
    private class StemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            strategy = new StemStrategy();
            refresh();
        }
    }
    
    private class FormButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            strategy = new FormStrategy();
            refresh();
        }
    }
    
    private class FeatureButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            strategy = new FeatureStrategy();
            refresh();
        }
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent ev) {
            if (!ev.getValueIsAdjusting() && getSelected() != null) {
                for (Listener listener : listeners) {
                    listener.selectedEntry(getSelected());
                }
            }
        }
    }
    
}
