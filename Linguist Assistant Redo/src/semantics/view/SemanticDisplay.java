package grammar.view;

import grammar.model.Constituent;
import grammar.model.Root;
import grammar.model.XMLParser;

import javax.swing.JButton;
import javax.swing.JPanel;

import commons.view.MainFrame;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SemanticDisplay extends JPanel {
    Constituent root;
    BlocksPanel blocksPanel;
    FeatureValuesPanel featureValuesPanel;
    private ButtonPanel buttonPanel;
    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnGenerate;
    private XMLParser parser;
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
       /* Constituent con = new Constituent("C", null);
        Constituent con2 = new Constituent("N", con);
        Constituent con3 = new Constituent("V", con);
        Constituent con4 = new Constituent("R", con);
        Constituent con5 = new Constituent("E", con);
        
        con.addChild(con2);
        con.addChild(con3);
        con.addChild(con4);
        con.addChild(con5);*/
        
        SemanticDisplay panel = new SemanticDisplay();
       // panel.updateConstituent(con);
        frame.setPanel(panel);
    }
    
    public SemanticDisplay() {
        initComponents();
        addComponents();
    }
    
    private void initComponents() {
    	parser=new XMLParser();
        blocksPanel = new BlocksPanel();
        blocksPanel.addBlockListener(new ImpBlockListener());
        featureValuesPanel = new FeatureValuesPanel();
        buttonPanel = new ButtonPanel();
        btnLoad=new JButton("Load XML");
        btnLoad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		readXML("data/example-new.xml");
        	}
        });
        btnSave=new JButton("Save XML");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		//writeXML();
        	}
        });
        btnGenerate=new JButton("Generate Text");
        btnGenerate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		readXML("data/infected eye 1-2-generated.xml");
        	}
        });
    }
    private void readXML(String filename)
    {
    	Constituent root = parser.read(filename);

         	
         	/*Block b=new Block(c);
         	blocksPanel.add(b);*/
    		 updateConstituent(root);

         
    }
    /*private void writeXML()
    {
        if(root!=null)
           parser.writeXML(filename,root);
    }*/
    private void addComponents() {
        setLayout(new MigLayout());
        add(blocksPanel);
        add(featureValuesPanel, "wrap");
        add(buttonPanel);
        add(btnLoad);
        add(btnSave);
        add(btnGenerate);
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
        	//System.out.println("Selected");
            featureValuesPanel.setConstituent(constituent);
        }

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {}

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {}
    }
}
