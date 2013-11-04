package semantics.view;

import grammar.model.Category;
import grammar.model.FileBrowsing;
import grammar.model.XMLParser;
import grammar.view.FeatureValuesListener;
import grammar.view.FeatureValuesPanel;
import grammar.view.GrammarEditorPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import lexicon.view.LexiconUI;
import ontology.controller.OntologyManager;
import semantics.model.Constituent;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SemanticEditorPanel extends JPanel {
    private Constituent root;
    private Constituent selectedConstituent;
    private BlocksPanel blocksPanel;
    private FeatureValuesPanel featureValuesPanel;
    //FileBrowsing browser;
    private List<Listener> listeners = new ArrayList<>();
    
    private ButtonPanel buttonPanel;
    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnGenerate;
    private JButton btnGrammar;
    private JButton btnLexicon;
    private JButton selectConstituent;
	private JTextArea txtTranslation;
    
	private XMLParser parser;
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
      
        SemanticEditorPanel panel = new SemanticEditorPanel();
        Category con = Category.getByName("Noun");
        //panel.updateConstituent(con);
        frame.setPanel(panel);
    }
    
    public interface Listener {
        void generate(Constituent constituent);
        void setConcept(Constituent constituent);
    }
    
    public SemanticEditorPanel() {
        initComponents();
        addComponents();
    }
    private void readXML(String filename) {
    	
    	Constituent root = parser.read(filename); 	
     	updateConstituent(root);
		//getTranslation(root);
		txtTranslation.repaint();
    }
/*	
    private void getTranslation(Category c) {
    	if (c.hasChildren()) {
    		 for(Category k: c.getChildren()) {
    			 getTranslation(k);
    		 }
    	}
    	else if (c.getTranslation() != null)
    		txtTranslation.append(c.getTranslation().toString() + " ");
    }
    */
    private void writeXML(String filename) {
        if(root != null)
           parser.writeXML(filename,root);
    }
    public void appendTranslation(String word)
    {
    	txtTranslation.append(word+" ");
    }
    public void clearTranslation()
    {
    	txtTranslation.setText("");
    }
    private void initComponents() {
    	parser = new XMLParser();
		txtTranslation = new JTextArea();
    	txtTranslation.setEditable(false);
    	txtTranslation.setPreferredSize(new Dimension(400,50));
    	//browser = new FileBrowsing();
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
        		String filename = getFile();
        		writeXML(filename);
        		JOptionPane.showMessageDialog(null,null,"XML Saved", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        
        btnGenerate = new JButton("Apply Rules");
        btnGenerate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		for (Listener listener : listeners) {
        		    listener.generate(root);
        		}
        	}
        });
        
        btnGrammar=new JButton("Edit Grammar");
        btnGrammar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		 MainFrame frame = new MainFrame();
        		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		     GrammarEditorPanel panel = new GrammarEditorPanel();
    		     frame.setPanel(panel);
        	}
        });
        btnLexicon=new JButton("View Lexicon");
        btnLexicon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
       		LexiconUI lex=new LexiconUI();
       	       
       	}
       });
        selectConstituent = new JButton("Select Constituent");
        selectConstituent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (selectedConstituent == null)
                     return;
                for (Listener listener : listeners) {
                    listener.setConcept(selectedConstituent);
                }
       	}
       });
    }
    private String getFile() {
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
        add(btnLexicon);
        add(selectConstituent);
		add(txtTranslation);
        //add(browser);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
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
            selectedConstituent = constituent;
            featureValuesPanel.setConstituent(constituent);
        }

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {}

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {}

		@Override
		public void tryDelete(Constituent category) {}
    }

}
