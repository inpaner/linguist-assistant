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
        Constituent con2 = new Constituent("N", null);
        con.addChild(con2);
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
    
    private class ImplementedBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {
            System.out.println("selected " + constituent.getLabel());
        }

        @Override
        public void droppedConstituent(Constituent source, Constituent destination) {
            System.out.println("Source: " + source.getLabel());
            System.out.println("Dest: " + destination.getLabel());
            
        }
    }
}
