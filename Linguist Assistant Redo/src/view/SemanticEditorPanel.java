package view;

import javax.swing.JPanel;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SemanticEditorPanel extends JPanel {
    Constituent root;
    BlocksPanel blocksPanel;
    FeatureValuesPanel featureValuesPanel;
    private ButtonPanel buttonPanel;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Constituent con = new Constituent("C", null);
        Constituent con2 = new Constituent("N", con);
        Constituent con3 = new Constituent("V", con);
        Constituent con4 = new Constituent("R", con);
        Constituent con5 = new Constituent("E", con);
        
        con.addChild(con2);
        con.addChild(con3);
        con.addChild(con4);
        con.addChild(con5);
        
        SemanticEditorPanel panel = new SemanticEditorPanel();
        panel.updateConstituent(con);
        frame.setPanel(panel);
    }
    
    public SemanticEditorPanel() {
        initComponents();
        addComponents();
    }
    
    private void initComponents() {
        blocksPanel = new BlocksPanel();
        blocksPanel.addBlockListener(new ImpBlockListener());
        featureValuesPanel = new FeatureValuesPanel();
        buttonPanel = new ButtonPanel();
    }
    
    private void addComponents() {
        setLayout(new MigLayout());
        add(blocksPanel);
        add(featureValuesPanel, "wrap");
        add(buttonPanel);
    }
    
    public void updateConstituent(Constituent root) {
        this.root = root;
        refresh();
    }
    
    public void refresh() {
        blocksPanel.updateRoot(root);
    }
    
    public void addBlockListener(BlockListener listener) {
        blocksPanel.addBlockListener(listener);
    }
    
    public void addFeatureValuesListener(FeatureValuesListener listener) {
        featureValuesPanel.addFeatureValuesListener(listener);
    }
    
    private class ImpBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {
            featureValuesPanel.setConstituent(constituent);
        }

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {}

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {}
    }
}
