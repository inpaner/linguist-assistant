package rule.spellout.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import grammar.model.Category;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class SpelloutUi extends JPanel {
    private HeaderPanel header;
    private CardLayout cards;
    private JPanel switchable;
    private List<Listener> listeners = new ArrayList<>();
    public interface Listener {
        void ok();
        void cancel();
    }

    public SpelloutUi(Category category) {
        header = new HeaderPanel(category);
        switchable = new JPanel();
        cards = new CardLayout();
        switchable.setLayout(cards);
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(new Ok());
        cancel.addActionListener(new Cancel());
        
        setLayout(new MigLayout());
        add(header, "wrap");
        add(switchable, "wrap");
        add(ok, "span, split, right");
        add(cancel);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public void addHeaderListener(HeaderPanel.Listener listener) {
        header.addListener(listener);
    }
    
    public void setPanel(Component panel) {
        switchable.removeAll();
        switchable.add(panel);
        switchable.invalidate();
        switchable.validate();
        
        switchable.repaint();
    }
    
    public class Ok implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.ok();
            }
        }
    }
    
    public class Cancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.cancel();
            }
        }
        
    }
}
