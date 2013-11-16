package rule.spellout;

import java.util.ArrayList;
import java.util.List;

import grammar.model.Category;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import rule.spellout.HeaderPanel.Listener;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class ModificationPanel extends JPanel {
    public List<Listener> listeners = new ArrayList<>();
        
    
    public interface Listener {
        void selectedStructures();
        void selectedMod(ModType type);
        void selectedTrigger();
    }
    
    public ModificationPanel() {
        /*
         * Initialize Components
         */
        // Structures
        JButton structures = new JButton("Structures");
        
        // Modification Radio Buttons
        ModRadio prefixRadio = new ModRadio("Prefix", ModType.PREFIX);
        ModRadio suffixRadio = new ModRadio("Suffix", ModType.SUFFIX);
        ModRadio infixRadio = new ModRadio("Infix", ModType.INFIX);
        ModRadio circumfixRadio = new ModRadio("Circumfix", ModType.CIRUMFIX);
        ModRadio newTranslationRadio = new ModRadio("New Translation", ModType.NEW_TRANSLATION);
        ModRadio addWordRadio = new ModRadio("Add Word", ModType.ADD_WORD);
        ButtonGroup modGroup = new ButtonGroup();
        modGroup.add(prefixRadio);
        modGroup.add(suffixRadio);
        modGroup.add(infixRadio);
        modGroup.add(circumfixRadio);
        modGroup.add(newTranslationRadio);
        modGroup.add(addWordRadio);
        
        Box radioBox = new Box(BoxLayout.X_AXIS);
        TitledBorder border = BorderFactory.createTitledBorder("Type of Modification");
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        radioBox.setBorder(border);
        radioBox.add(prefixRadio);
        radioBox.add(suffixRadio);
        radioBox.add(infixRadio);
        radioBox.add(circumfixRadio);
        radioBox.add(newTranslationRadio);
        radioBox.add(addWordRadio);
        
        // Trigger word
        JButton triggerWord = new JButton("Triggers");
        JLabel dummy = new JLabel("WORDS HERE");
        JCheckBox triggerExcluded = new JCheckBox("Excluded");
        
        setLayout(new MigLayout("wrap 2"));
        add(structures);
        add(radioBox);
        add(triggerWord);
        add(dummy, "span, split 2");
        add(triggerExcluded);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    /**
     * Serves as a struct to contain the Modification Enum
     */
    @SuppressWarnings("serial")
    private class ModRadio extends JRadioButton {
        private ModType mod;
        private ModRadio(String title, ModType mod) {
            super(title);
            this.mod = mod;
        }
    }
}
