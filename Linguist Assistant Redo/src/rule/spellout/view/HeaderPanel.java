package rule.spellout.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import grammar.model.Category;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import rule.spellout.SpelloutType;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class HeaderPanel extends JPanel {
    private Category category;
    private List<Listener> listeners = new ArrayList<>();
    private JTextField ruleName;
    private JCheckBox statusBox;
    
    public interface Listener {
        void typeChanged(SpelloutType spelloutType);
    }
    
    public HeaderPanel(Category category) {
        this.category = category;
        
        /*
         * Init components
         */
        // Category
        JLabel categoryNameLabel = new JLabel("Category: ");
        JTextField categoryName = new JTextField(15);
        categoryName.setEditable(false);
        categoryName.setText(category.getLabel());
        
        JLabel ruleNameLabel = new JLabel("Rule's Name: ");
        ruleName = new JTextField(40);
        
        statusBox = new JCheckBox("On");
        statusBox.setSelected(true);
        Box statusContainer = new Box(BoxLayout.X_AXIS);
        TitledBorder border = BorderFactory.createTitledBorder("Status");
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        statusContainer.setBorder(border);
        statusContainer.add(statusBox);
        
        // Type of Rule Radio Buttons
        TypeRadio simpleButton = new TypeRadio("Simple", SpelloutType.SIMPLE);
        TypeRadio tableButton = new TypeRadio("Table", SpelloutType.TABLE);
        TypeRadio morphophonemicButton = new TypeRadio("Morphophonemic", SpelloutType.MORPHOPHONEMIC);
        TypeRadio formButton = new TypeRadio("Form Selection", SpelloutType.FORM);
        TypeRadio phraseButton = new TypeRadio("Phrase Builder", SpelloutType.PHRASE);
        
        ButtonGroup ruleTypeGroup = new ButtonGroup();
        ruleTypeGroup.add(simpleButton);
        ruleTypeGroup.add(tableButton);
        ruleTypeGroup.add(morphophonemicButton);
        ruleTypeGroup.add(formButton);
        ruleTypeGroup.add(phraseButton);
        simpleButton.setSelected(true);
        RadioListener radioListener = new RadioListener();
        
        Box ruleTypeContainer = new Box(BoxLayout.X_AXIS);
        border = BorderFactory.createTitledBorder("Type of Rule");
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        ruleTypeContainer.setBorder(border);
        ruleTypeContainer.add(simpleButton);
        ruleTypeContainer.add(tableButton);
        ruleTypeContainer.add(morphophonemicButton);
        ruleTypeContainer.add(formButton);
        ruleTypeContainer.add(phraseButton);
        
        /*
         * Add internal listeners
         */
        for (Enumeration<AbstractButton> buttons = ruleTypeGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addActionListener(radioListener);
        }
        
        /*
         * Add components
         */
        setLayout(new MigLayout("wrap 2"));
        add(categoryNameLabel);
        add(categoryName);
        add(ruleNameLabel);
        add(ruleName);
        add(statusContainer);
        add(ruleTypeContainer);        
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public boolean getStatus() {
        return statusBox.isSelected();
    }
    
    /**
     * Serves as a struct to contain the SpelloutType Enum
     */
    @SuppressWarnings("serial")
    private class TypeRadio extends JRadioButton {
        private SpelloutType spelloutType;
        private TypeRadio(String title, SpelloutType spelloutType) {
            super(title);
            this.spelloutType = spelloutType;
        }
    }
    
    /*
     * Internal listeners
     */
    private class RadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TypeRadio radio = (TypeRadio) e.getSource();
            for (Listener listener : listeners) {
                listener.typeChanged(radio.spelloutType);
            }
        }
    }
}
