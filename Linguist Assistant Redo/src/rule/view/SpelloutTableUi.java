package rule.view;

import grammar.model.Category;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import rule.FeatureSelector;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class SpelloutTableUi extends JPanel {
    private Category category;
    private JTable table;
    private List<FeatureButton> rowButtons;
    private List<FeatureButton> colButtons;
    private FeatureButton cornerButton;
    private SpelloutTableModel model;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Category category = Category.getByName("Noun");
        SpelloutTableUi ui = new SpelloutTableUi(category);
        frame.setPanel(ui);
    }
    
    public SpelloutTableUi(Category category) {
        this.category = category;
        rowButtons = new ArrayList<>();
        colButtons = new ArrayList<>();
        cornerButton = new FeatureButton(new RuleButtonListener(), "corner");
        
        FeatureButton b1 = new FeatureButton(new RuleButtonListener(), "b1");
        
        FeatureButton b2 = new FeatureButton(new RuleButtonListener(), "b2");
        
        rowButtons.add(b1);
        colButtons.add(b2);
        
        model = new SpelloutTableModel(rowButtons, colButtons);
        table = new JTable();
        table.setModel(model);
        
        TableColumn tc = table.getColumnModel().getColumn(0);
        
        ButtonColumn buttonColumn = new ButtonColumn(table, firstColAction, 0);
        tc.setHeaderRenderer(cornerButton);
        
        setLayout(new MigLayout());
        
        JScrollPane tablePane = new JScrollPane(table); 
        add(tablePane);
    }
    
    private class RuleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();  
            FeatureButton button = (FeatureButton) source;
            FeatureSelector selector = FeatureSelector.getSelector(
                    category, button.getTitle(), button.getFeaturesList());
            
            button.setTitle(selector.getTitle());
            button.setFeaturesList(selector.getFeatures());
        }
    }
    
    @SuppressWarnings("serial")
    Action firstColAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            FeatureButton button = rowButtons.get(modelRow);
            System.out.println(button.getTitle());
            FeatureSelector selector = FeatureSelector.getSelector(
                    category, button.getTitle(), button.getFeaturesList());
            button.setTitle(selector.getTitle());
            button.setFeaturesList(selector.getFeatures());
            
            
            
        }
    };
}
