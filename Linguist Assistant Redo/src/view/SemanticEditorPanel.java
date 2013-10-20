package view;

import grammar.model.Constituent;
import grammar.model.FileBrowsing;
import grammar.model.XMLParser;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import commons.view.MainFrame;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SemanticEditorPanel extends JPanel {
    Constituent root;
    BlocksPanel blocksPanel;
    FeatureValuesPanel featureValuesPanel;
    //FileBrowsing browser;
    private ButtonPanel buttonPanel;
    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnGenerate;
    private JButton btnGrammar;
	 private JTextArea txtTranslation;
    private XMLParser parser;
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
    private void readXML(String filename)
    {
    	
    	Constituent root = parser.read(filename);

         	
         	/*Block b=new Block(c);
         	blocksPanel.add(b);*/
    		 updateConstituent(root);
			getTranslation(root);
    		 txtTranslation.repaint();
	

         
    }
	private void getTranslation(Constituent c)
    {
    	if(c.hasChildren())
    	{
    		 for(Constituent k: c.getChildren())
    		 {
    			 
    			getTranslation(k);
    		 }
    	}
    	else  if(c.getTranslation()!=null)
    		txtTranslation.append(c.getTranslation().toString()+" ");
    	
    		
    }
    private void writeXML(String filename)
    {
        if(root!=null)
           parser.writeXML(filename,root);
    }
    private void initComponents() {
    	parser=new XMLParser();
		txtTranslation=new JTextArea();
    	txtTranslation.setEditable(false);
    	txtTranslation.setPreferredSize(new Dimension(400,50));
    	//browser=new FileBrowsing();
        blocksPanel = new BlocksPanel();
        blocksPanel.addBlockListener(new ImpBlockListener());
        featureValuesPanel = new FeatureValuesPanel();
        buttonPanel = new ButtonPanel();
        btnLoad=new JButton("Load XML");
        btnLoad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        			String filename=getFile();
		        
		        readXML(filename);}
        	
        });
        btnSave=new JButton("Save XML");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String filename=getFile();
        		writeXML(filename);
        		JOptionPane.showMessageDialog(null,null,"XML Saved", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        btnGenerate=new JButton("Generate Text");
        btnGenerate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		txtTranslation.setText("");
        		readXML("data/infected eye 1-2-generated.xml");
        	}
        });
        btnGrammar=new JButton("Edit Grammar");
        btnGrammar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		 MainFrame frame = new MainFrame();
        	        GrammarEditorPanel panel = new GrammarEditorPanel();
        	        frame.setPanel(panel);
        	        //BEWARE: closing the MainFrame with the grammar editor will also close the main MainFrame
        	}
        });
    }
    private String getFile()
    {
    	JFileChooser fileChooser = new JFileChooser();
		String filename=new String();
		 
        // For Directory
        //fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 
        // For File
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 
        fileChooser.setAcceptAllFileFilterUsed(false);
 
        int rVal = fileChooser.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
          filename=fileChooser.getSelectedFile().toString();}
         return filename;
    }
    private void addComponents() {
        setLayout(new MigLayout("wrap 2"));
        add(blocksPanel, "flowy");
        add(featureValuesPanel, "flowy");
        add(buttonPanel, "flowy, cell 0 0");
        add(btnLoad);
        add(btnSave);
        add(btnGenerate);
        add(btnGrammar);
		add(txtTranslation);
        //add(browser);
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
