package view;

import javax.swing.JPanel;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

public class SemanticDisplay extends JPanel {
    Constituent root;
    BlocksPanel blocksPanel;
    FeatureValuesPanel featureValuesPanel;
    
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
        
        SemanticDisplay panel = new SemanticDisplay(con);
        frame.setPanel(panel);
    }
    
    public SemanticDisplay(Constituent root) {
        this.root = root;
        initComponents();
        addComponents();
    }
    
    private void initComponents() {
        blocksPanel = new BlocksPanel(root);
        blocksPanel.addBlockListener(new ImplementedBlockListener());
        featureValuesPanel = new FeatureValuesPanel();
        featureValuesPanel.setConstituent(root);
    }
    
    private void addComponents() {
        setLayout(new MigLayout());
        add(blocksPanel);
        add(featureValuesPanel);
    }
    
    // TODO move to controller
    private class ImplementedBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {
            System.out.println("selected " + constituent.getLabel());
            featureValuesPanel.setConstituent(constituent);
        }

        @Override
        public void droppedConstituent(Constituent source, Constituent destination, int index) {
            destination.moveChild(source, index);
            blocksPanel.updateRoot(root);
            
        }
    }
}
