package rule.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class SpelloutTableUi extends JPanel {
    private JTable table;
    private List<JButton> rowButtons;
    private List<JButton> colButtons;
    private JButton cornerButton;
    private SpelloutTableModel model;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        SpelloutTableUi ui = new SpelloutTableUi();
        frame.setPanel(ui);
    }
    
    public SpelloutTableUi() {
        rowButtons = new ArrayList<>();
        colButtons = new ArrayList<>();
        cornerButton = new JButton("Corner");
        
        FeatureButton b1 = new FeatureButton(new RuleButtonListener());
        JButton b2 = new JButton("Top");
        
        rowButtons.add(b1);
        colButtons.add(b2);
        
        model = new SpelloutTableModel(rowButtons, colButtons);
        table = new JTable();
        table.setModel(model);
        
        TableColumn tc = table.getColumnModel().getColumn(0);
        tc.setHeaderRenderer(b1);
        
        setLayout(new MigLayout());
        
        JScrollPane tablePane = new JScrollPane(table); 
        add(tablePane);
    }
    
    private class RuleButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();  
            
            System.out.println("clicked");
            
        }
        
    }
}
