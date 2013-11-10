package lexicon.view;

import grammar.model.Category;
import grammar.model.Feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import lexicon.model.Entry;
import lexicon.model.Form;

@SuppressWarnings("serial")
public class SelectEntryDialog extends JDialog {
    private Entry selected;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public void select(Entry entry);
        public void cancel(Entry entry);
    }
    
    public SelectEntryDialog(Listener listener, Category category) {
        listeners.add(listener);
        
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        
        LexiconList list = new LexiconList(category);
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
    
    private class ListListener implements LexiconList.Listener {
        @Override
        public void selectedEntry(Entry entry) {
            selected = entry;
        }

        @Override
        public void featureValueChanged(Entry entry, Feature feature,
                String value) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void formValueChanged(Entry entry, Form form, String value) {
            // TODO Auto-generated method stub
            
        }
    }
    
    private class SelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.select(selected);
            }
            dispose();
        }
    }
    
    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.cancel(selected);
            }
            dispose();
        }
    }
}
