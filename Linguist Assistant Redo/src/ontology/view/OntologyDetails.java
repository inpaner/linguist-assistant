package ontology.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import commons.view.MainFrame;
import net.miginfocom.swing.MigLayout;

public class OntologyDetails extends JPanel {
    private final int FIELD_WIDTH = 20;
    private final int AREA_HEIGHT = 3;
    private JTextField stemField;
    private JTextField senseField;
    private JTextArea glossArea;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        JPanel panel = new OntologyDetails();
        frame.setPanel(panel);
    }
    
    public OntologyDetails() {
        
        setLayout(new MigLayout());
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new MigLayout("wrap 2"));
        
        JLabel stem = new JLabel("Stem: ");
        stemField = new JTextField(FIELD_WIDTH);
        
        JLabel sense = new JLabel("Sense: ");
        senseField = new JTextField(3);
        
        JLabel gloss = new JLabel("Gloss: ");
        glossArea = new JTextArea(AREA_HEIGHT, FIELD_WIDTH);
        glossArea.setWrapStyleWord(true);
        glossArea.setLineWrap(true);
        
        detailsPanel.add(stem);
        detailsPanel.add(stemField);
        detailsPanel.add(sense);
        detailsPanel.add(senseField);
        detailsPanel.add(gloss);
        detailsPanel.add(glossArea);
        
        add(detailsPanel);
    }
}
