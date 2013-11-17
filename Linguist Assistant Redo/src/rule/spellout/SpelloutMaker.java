package rule.spellout;

import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import commons.main.MainFrame;
import grammar.model.Category;
import rule.model.Rule;
import rule.spellout.view.HeaderPanel;
import rule.spellout.view.SpelloutUi;
import rule.spellout.view.HeaderPanel.Listener;

public class SpelloutMaker {
    private Spellout strategy;
    private SpelloutUi ui;
    private Category category;
    private JDialog dialog;
    private Rule rule;
    
    public static Rule create(Category category) {
        SpelloutMaker maker = new SpelloutMaker(category);
        return maker.rule;
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        SpelloutMaker maker = new SpelloutMaker(Category.getByName("Noun"));
        
    }
    
    public SpelloutMaker(Category category) {
        dialog = new JDialog();
        dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
        dialog.setSize(550, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new MigLayout());
        
        this.category = category;
        strategy = new Simple(category);
        ui = new SpelloutUi(category);
        ui.addListener(new Listener());
        ui.addHeaderListener(new HeaderListener());
        ui.setPanel(strategy.getView());
        
        dialog.setContentPane(ui);
        dialog.setVisible(true);
        
    }
    
    private class Listener implements SpelloutUi.Listener {

        @Override
        public void ok() {
            rule = strategy.getRule();
            dialog.dispose();
        }

        @Override
        public void cancel() {
            rule = null;
            dialog.dispose();
        }
        
    }
    
    private class HeaderListener implements HeaderPanel.Listener {
        @Override
        public void typeChanged(SpelloutType spelloutType) {
            switch (spelloutType) {
                case FORM:
                    break;
                case MORPHOPHONEMIC:
                    break;
                case PHRASE:
                    break;
                case SIMPLE:
                    strategy = new Simple(category);
                    break;
                case TABLE:
                    strategy = new Table(category);
                    break;
                default:
                    break;
            }
            ui.setPanel(strategy.getView());
            
        }
        
    }
}
