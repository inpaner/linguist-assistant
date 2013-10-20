/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.SyntaxTreeNode;
import grammar.model.Constituent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.xml.bind.Marshaller.Listener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aram
 */
public class LEXICONUI extends JFrame {

   protected LEXICONUI.Listener l;
   public JButton stems;
   public JButton features;
   public JButton forms;
   public JButton sdomains;
   public JButton newstem;
   public JButton search;
   public JButton addcolumn;
   public JButton newfeature;
   public JButton editfeature;
   public JButton subset;
   public JButton newform;
   public JButton formrules;
   public JButton awords;
   public JButton edomains;
   public JButton vdomains;
   public JButton export;
   public JLabel sCategory;
   public JLabel view;
   public JLabel function;
   private DefaultTableModel model;
   
    JComboBox syntacticCategory = new JComboBox();
    private JTable table;
    private JScrollPane tablePane;
   
   public LEXICONUI(){
       initialize();
       setBounds();
       setFrame();
       addToFrame();
       buttonFunctions();
       
   }
   
   public void initialize(){
      // syntacticCategory.setModel(new DefaultComboBoxModel(new String[] {"Noun", "Verb", "Adjective", "Adverb", "Adposition", "Conjunction", "Phrasal", "Particle"}));
       for(Constituent constituent : Constituent.getAllConstituents())
       {
    	   syntacticCategory.addItem(constituent.getLabel());
       }
       syntacticCategory.setVisible(false);
       sCategory = new JLabel("Syntactic Category");
       view = new JLabel("View");
       function = new JLabel("Function");
       stems = new JButton("Stems");
       features = new JButton("Features");
       forms = new JButton("Forms");
       sdomains = new JButton("Semantic Domains");
       newstem = new JButton("New Stem");
       search = new JButton("Search");
       addcolumn = new JButton("Add Column");
       newfeature = new JButton("New Feature");
       editfeature = new JButton("Edit Feature");
       subset = new JButton("Subset");
       newform = new JButton("New Form");
       formrules = new JButton("Form Rules");
       export = new JButton("Export to File");
       awords = new JButton("Add Words");
       edomains = new JButton("Edit Domains");
       vdomains = new JButton("View Domains");
       
  
       
       
       l = new LEXICONUI.Listener();
     //  syntacticCategory.addMouseListener(l);
    
   }
   
   public void setBounds(){
       syntacticCategory.setBounds(150, 24, 200, 20);
       sCategory.setBounds(30,24,150,20);
       view.setBounds(487, 5, 80,20);
       function.setBounds(790,5, 80,20);
       stems.setBounds(400,24,80,20);
       features.setBounds(520,24,100,20);
       forms.setBounds(400,50,80,20);
       sdomains.setBounds(520,50,150,20);
       newstem.setBounds(700,24,100,20);
       search.setBounds(830,24,80,20);
       addcolumn.setBounds(700,50,130,20);
       newfeature.setBounds(700,24,130,20);
       editfeature.setBounds(700,50,130,20);
       subset.setBounds(850,24,80,20);
       newform.setBounds(700,24,130,20);
       formrules.setBounds(700,50,130,20);
       export.setBounds(850,24,120,20);
       awords.setBounds(700,24,130,20);
       edomains.setBounds(700,50,130,20);
       vdomains.setBounds(850,24,120,20);
   }
   
   public void setFrame(){
       
          this.setSize(1000, 700);
          getContentPane().setLayout(null);
          this.setVisible(true);
   }
   
   public void addToFrame(){
       getContentPane().add(syntacticCategory);
       getContentPane().add(sCategory);
       getContentPane().add(view);
       getContentPane().add(function);
       getContentPane().add(stems);
       getContentPane().add(features);
       getContentPane().add(forms);
       getContentPane().add(sdomains);
       getContentPane().add(newstem);
       getContentPane().add(search);
       getContentPane().add(addcolumn);
       getContentPane().add(newfeature);
       getContentPane().add(editfeature);
       getContentPane().add(subset);
       getContentPane().add(newform);
       getContentPane().add(formrules);
       getContentPane().add(export);
       getContentPane().add(awords);
       getContentPane().add(edomains);
       getContentPane().add(vdomains);
       
       table = new JTable();
      model= new DefaultTableModel(
    	       	new Object[][] {
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       		{null, null},
    	       	},
    	       	new String[] {
    	       		"Stem", "Gloss"
    	       	}
    	       );
       table.setModel(model);
       table.setBounds(27, 101, 943, 550);
       tablePane=new JScrollPane(table);
       tablePane.setBounds(27, 101, 943, 550);
       getContentPane().add(tablePane);
       
       syntacticCategory.setVisible(true);
       sCategory.setVisible(true);
       view.setVisible(true);
       function.setVisible(true);
       stems.setVisible(true);
       features.setVisible(true);
       forms.setVisible(true);
       sdomains.setVisible(true);
       newstem.setVisible(true);
       search.setVisible(true);
       addcolumn.setVisible(true);
       newfeature.setVisible(false);
       editfeature.setVisible(false);
       subset.setVisible(false);
       newform.setVisible(false);
       formrules.setVisible(false);
       export.setVisible(false);
       awords.setVisible(false);
       edomains.setVisible(false);
       vdomains.setVisible(false);
       
       
   }
   
   public void buttonFunctions(){
       
      stems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			newstem.setVisible(true);
                        search.setVisible(true);
                        addcolumn.setVisible(true);
                        newfeature.setVisible(false);
                        editfeature.setVisible(false);
                        subset.setVisible(false);
			newform.setVisible(false);
                        formrules.setVisible(false);
                        export.setVisible(false);
                        awords.setVisible(false);
                        edomains.setVisible(false);
                        vdomains.setVisible(false);
           //TODO: remove extra columns
            model.addColumn("Comments");
            model.addColumn("Sample Sentences");
			}
		});
      
       features.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			newstem.setVisible(false);
                        search.setVisible(false);
                        addcolumn.setVisible(false);
                        newfeature.setVisible(true);
                        editfeature.setVisible(true);
                        subset.setVisible(true);
			newform.setVisible(false);
                        formrules.setVisible(false);
                        export.setVisible(false);
                        awords.setVisible(false);
                        edomains.setVisible(false);
                        vdomains.setVisible(false);
            //TODO: change table model to include columns corresponding to features of selected POS
			}
		});
       
       forms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			newstem.setVisible(false);
                        search.setVisible(false);
                        addcolumn.setVisible(false);
                        newfeature.setVisible(false);
                        editfeature.setVisible(false);
                        subset.setVisible(false);
                        newform.setVisible(true);
                        formrules.setVisible(true);
                        export.setVisible(true);
			awords.setVisible(false);
                        edomains.setVisible(false);
                        vdomains.setVisible(false);
           //TODO: change table model to include columns based on forms of selected POS.
			}
		});
       
       sdomains.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			newstem.setVisible(false);
                        search.setVisible(false);
                        addcolumn.setVisible(false);
                        newfeature.setVisible(false);
                        editfeature.setVisible(false);
                        subset.setVisible(false);
                        newform.setVisible(false);
                        formrules.setVisible(false);
                        export.setVisible(false);
			awords.setVisible(true);
                        edomains.setVisible(true);
                        vdomains.setVisible(true);
           //TODO: remove extra columns
           model.addColumn("Semantic Domains");

			}
		});
       syntacticCategory.addActionListener(new ActionListener()
       {
    	   
    	   public void actionPerformed(ActionEvent arg0) {
    		   	populateTable(syntacticCategory.getSelectedItem().toString());
   			}
   		});
   }
   public void populateTable(String pos)
   {
	   //get all concepts/definitions from DB that have pos as a label
   }
    public static void main(String[] args) {
        LEXICONUI lui = new LEXICONUI();
     
    }
    
    
    
     public class Listener implements MouseListener {


       
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) throws NullPointerException {
         

             }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
        }
}

