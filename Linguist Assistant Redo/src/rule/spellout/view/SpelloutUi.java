package rule.spellout.view;

import java.awt.CardLayout;
import java.awt.Component;

import grammar.model.Category;

import javax.swing.JPanel;

public class SpelloutUi extends JPanel {
    private HeaderPanel header;
    private CardLayout cards;
    private JPanel switchable;
    
    public interface Listener extends HeaderPanel.Listener {}

    public SpelloutUi(Category category) {
        header = new HeaderPanel(category);
        switchable = new JPanel();
        cards = new CardLayout();
        switchable.setLayout(cards);    
    }
    
    public void addListener(Listener listener) {
        header.addListener(listener);
    }
    
    public void setPanel(Component panel) {
        cards.addLayoutComponent(panel, "root");
        cards.show(switchable, "root");
    }
    
}
