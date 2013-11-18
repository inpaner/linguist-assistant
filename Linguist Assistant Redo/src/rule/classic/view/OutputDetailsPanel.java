package rule.classic.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import rule.classic.OutputCons;


/*
 * Controller + View because time is running out
 */
public class OutputDetailsPanel extends JPanel {
    private OutputCons cons;
    private JCheckBox delete;
        
    public OutputDetailsPanel() {
        delete = new JCheckBox("Delete");
        
        JButton addOutput = new JButton("+");
        JButton deleteOutput = new JButton("—");
        setLayout(new MigLayout());
    }
    
    public void setConstituent(OutputCons cons) {
        this.cons = cons;
        
        delete.setSelected(cons.isToDelete());
                
    }
}
