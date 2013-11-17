package rule.classic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import rule.model.output.OutputType;

public class OutputTypeSelector extends JDialog {
    public static void main(String[] args) {
        System.out.println(OutputTypeSelector.select());
    }
    
    public static OutputType select() {
        OutputTypeSelector selector = new OutputTypeSelector();
        return selector.result;
    }
    
    private JComboBox<OutputType> types;
    private OutputType result;
    public OutputTypeSelector() {
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setSize(200, 100);
        setLocationRelativeTo(null);
        
        OutputType[] array = OutputType.class.getEnumConstants();
        Vector<OutputType> vector = new Vector(Arrays.asList(array));
        types = new JComboBox<>(vector);
        
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(new Ok());
        cancel.addActionListener(new Cancel());
        
        JPanel content = new JPanel();
        content.setLayout(new MigLayout("center"));
        content.add(types, "wrap");
        content.add(ok, "span, split, right");
        content.add(cancel);
        
        setContentPane(content);
        setVisible(true);
    }
    
    private class Ok implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = types.getItemAt(types.getSelectedIndex());
            dispose();
        }
    }
    
    private class Cancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = null;
            dispose();
        }
    }
}
