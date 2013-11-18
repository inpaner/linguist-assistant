package rule.classic.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        
        JButton setFeature = new JButton("Set Features");
        JButton setTranslation = new JButton("Set Translation");
        setLayout(new MigLayout());
        add(delete);
        add(setFeature);
        add(setTranslation);
        
    }
    
    public void setConstituent(OutputCons cons) {
        this.cons = cons;
        delete.setSelected(cons.isToDelete());
    }
    
    public class SetFeature implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }
}
