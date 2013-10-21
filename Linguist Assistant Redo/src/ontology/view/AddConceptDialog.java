package ontology.view;

import grammar.model.Constituent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;

@SuppressWarnings("serial")
public class AddConceptDialog extends JDialog {
    private static JComboBox<Constituent> constituentBox;
    private static JTextField stemField;
    private static JTextField glossField;
    private JButton ok;
    
    public static Concept getInstance() {
        new AddConceptDialog();
        String stem = stemField.getText();
        String gloss = glossField.getText();
        Constituent constituent = (Constituent) constituentBox.getSelectedItem();
        Concept concept = Concept.getEmpty(constituent);
        concept.setStem(stem);
        concept.setGloss(gloss);
        
        return concept;
    }
    
    private AddConceptDialog() {
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        Vector<Constituent> constituents = new Vector<>(Constituent.getAllConstituents());
        constituentBox = new JComboBox<>(constituents);
        
        JPanel panel = new JPanel();
        JLabel stemLabel = new JLabel("Stem: ");
        stemField = new JTextField(20);
        JLabel glossLabel = new JLabel("Gloss: ");
        glossField = new JTextField(20);
        
        ok = new JButton("Add");
        ok.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        
        panel.setLayout(new MigLayout("wrap 2"));
        panel.add(constituentBox, "span, split, wrap");
        panel.add(stemLabel);
        panel.add(stemField);
        panel.add(glossLabel);
        panel.add(glossField);
        panel.add(ok);
        setContentPane(panel);
        setVisible(true);
    }

}
