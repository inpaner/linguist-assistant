package ontology.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;

@SuppressWarnings("serial")
public class SelectConceptDialog extends JDialog {
    private Concept selectedConcept;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public void select(Concept concept);
        public void cancel(Concept concept);
    }
    
    public SelectConceptDialog(Listener listener) {
        listeners.add(listener);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        
        selectedConcept = null;
        
        ConceptList list = new ConceptList();
        list.addListener(new ListListener());
        JButton select = new JButton("Select");
        JButton cancel = new JButton("Cancel");
        select.addActionListener(new SelectListener());
        cancel.addActionListener(new CancelListener());
        
        JPanel content = new JPanel();
        content.setLayout(new MigLayout());
        content.add(list, "wrap");
        content.add(select,"span, split, right");
        content.add(cancel);
        
        setContentPane(content);
        setVisible(true);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    private class ListListener implements ConceptList.Listener {
        @Override
        public void selectedConcept(Concept concept) {
            selectedConcept = concept;
        }
    }
    
    private class SelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.select(selectedConcept);
            }
            dispose();
        }
    }
    
    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.cancel(selectedConcept);
            }
            dispose();
        }
    }
}
