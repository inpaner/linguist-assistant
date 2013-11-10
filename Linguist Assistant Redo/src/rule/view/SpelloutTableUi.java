package rule.view;

import grammar.model.Category;
import grammar.model.Feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import rule.FeatureSelector;
import rule.model.Rule;
import rule.model.RuleSet;
import rule.model.input.And;
import rule.model.input.HasFeature;
import rule.model.input.Input;
import rule.model.input.Or;
import rule.model.output.ForceTranslation;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class SpelloutTableUi extends JPanel {
    private Category category;
    private JTable table;
    private List<FeatureButton> rowButtons;
    private List<FeatureButton> colButtons;
    private FeatureButton cornerButton;
    private SpelloutTableModel model;
    RuleButtonListener buttonListener;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Category category = Category.getByName("Noun");
        SpelloutTableUi ui = new SpelloutTableUi(category);
        frame.setPanel(ui);
    }
    
    public SpelloutTableUi(Category category) {
        this.category = category;
        JButton addRow = new JButton("Add Row");
        JButton addCol = new JButton("Add Column");
        addRow.addActionListener(new AddRow());
        addCol.addActionListener(new AddCol());
        
        /*
         * Table
         */
        rowButtons = new ArrayList<>();
        colButtons = new ArrayList<>();
        buttonListener = new RuleButtonListener();
        cornerButton = new FeatureButton(buttonListener);
        
        FeatureButton initialRow = new FeatureButton(buttonListener);
        FeatureButton initialCol = new FeatureButton(buttonListener);
        
        rowButtons.add(initialRow);
        colButtons.add(initialCol);
        
        model = new SpelloutTableModel(rowButtons, colButtons);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        new ButtonColumn(table, firstColAction, 0);
        
        TableColumn tc = table.getColumnModel().getColumn(0);
        tc.setHeaderRenderer(cornerButton);
        
        tc = table.getColumnModel().getColumn(1);
        tc.setHeaderRenderer(initialCol);
        JScrollPane tablePane = new JScrollPane(table);
        
        /*
         * Layout
         */
        setLayout(new MigLayout());
        add(addRow, "span, split");
        add(addCol, "wrap");
        add(tablePane);
    }
    
    public RuleSet getRules() {
        RuleSet result = new RuleSet();
        Input cornerInputs = null;
        
        if (!cornerButton.getFeaturesList().isEmpty()) {
            cornerInputs = getInput(cornerButton.getFeaturesList());
        }
        
        for (FeatureButton row : rowButtons) {
            Input rowInputs = getInput(row.getFeaturesList());
            for (FeatureButton col : colButtons) {
                Input colInputs = getInput(col.getFeaturesList());
                
                int rowIndex = row.getRow();
                int colIndex = col.getColumn();
                String translation = (String) model.getValueAt(rowIndex, colIndex);
                
                And input = new And();
                input.addRule(rowInputs);
                input.addRule(colInputs);
                if (cornerInputs != null) {
                    input.addRule(cornerInputs);
                }
                
                ForceTranslation output = new ForceTranslation();
                output.setKey("root");
                output.setTranslation(translation);
                
                Rule rule = new Rule();
                rule.setInput(input);
                rule.addOutput(output);
                
                result.addRule(rule);
            }
        }
        
        return result;
    }
    
    private Input getInput(List<List<Feature>> featuresList) {
        And result = new And();
        
        for (List<Feature> features : featuresList) {
            Or or = new Or();
            for (Feature feature : features) {
                HasFeature hasFeature = new HasFeature(feature);
                or.addRule(hasFeature);
            }
            result.addRule(or);
        }
        
        return result;
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
    
    private class AddRow implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FeatureButton newRow = new FeatureButton(buttonListener);
        rowButtons.add(newRow);
        model.addRow();
        model.fireTableDataChanged();
    }
    }
    
    private class AddCol implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FeatureButton newCol = new FeatureButton(buttonListener);
        colButtons.add(newCol);
        TableColumn dummy = new TableColumn(model.getColumnCount() - 1);
        dummy.setHeaderRenderer(newCol);
        table.addColumn(dummy);
        
        model.addColumn();   
        model.fireTableDataChanged();
    }
    }
}
