package grammar.view;

import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AddDialog extends JDialog {
    public AddDialog() {
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        JPanel panel = new JPanel();
        panel.add(new JLabel("Add constituent lel"));
        setContentPane(panel);
        setVisible(true);
    }
    
    

}
