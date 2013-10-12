/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uitextgen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class UITEXTGEN extends JFrame implements ActionListener {
        protected JLabel bg;
       
        protected ImageIcon[] saveIcon;
        protected ImageIcon[] loadIcon;
        protected ImageIcon[] generateIcon;
        protected ImageIcon[] addButtonIcon;
   
        private JLabel save;
        private JLabel load;
        private JLabel generate;
        private JLabel addButton;
   
        public JButton CL;
        public JButton SubCl;
        public JButton NP;
        public JButton Adj;
        public JButton AdjP;
        public JButton Verb;
        public JButton Noun;
        public JButton VP;
                
        protected Listener l;
     
      public UITEXTGEN() throws ClassNotFoundException {
        try {
        	System.out.println("Initializing UI");
            buttons();
            initialize();
            setBounds();
            setFrame();
            addToFrame();
        } catch (SQLException ex) {
            Logger.getLogger(UITEXTGEN.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     }
     private void initialize() throws ClassNotFoundException, SQLException {
         
        saveIcon = new ImageIcon[2];  
        saveIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("SAVE_OFF.png"));
        saveIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("SAVE_ON.png"));
        
        loadIcon = new ImageIcon[2];  
        loadIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("LOAD_OFF.png"));
        loadIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("LOAD_ON.png"));
        
        generateIcon = new ImageIcon[2];  
        generateIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("GENERATE_OFF.png"));
        generateIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("GENERATE_ON.png"));
        
        addButtonIcon = new ImageIcon[2];  
        addButtonIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("PLUS_OFF.png"));
        addButtonIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("PLUS_ON.png"));
       
        save = new JLabel(saveIcon[0]);
        load = new JLabel(loadIcon[0]);
        generate = new JLabel(generateIcon[0]);
        addButton = new JLabel(addButtonIcon[0]);
    
         bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("bg.png")));
       
         
        l = new Listener();
         save.addMouseListener(l);
         load.addMouseListener(l);
        generate.addMouseListener(l);
        addButton.addMouseListener(l);

         
    }
         private void setBounds() {
            bg.setBounds(0, 0, bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
            save.setBounds(748, 103, save.getIcon().getIconWidth(), save.getIcon().getIconHeight());
            load.setBounds(760, 143, load.getIcon().getIconWidth(), load.getIcon().getIconHeight());
            generate.setBounds(740, 183, generate.getIcon().getIconWidth(), generate.getIcon().getIconHeight());
            addButton.setBounds(690, 530, addButton.getIcon().getIconWidth(), addButton.getIcon().getIconHeight());

    }
          
        private void setFrame() {
            this.setSize(bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
            //this.setBounds(10,10,1024,768);
            this.setLayout(null);
            this.setVisible(true);
        }

    private void addToFrame() {
    	
        this.add(save);
        this.add(load);
        
        this.add(generate);
        this.add(addButton);
        //this.add(bg);
      
    
        
        
       
    }
    
    private void buttons(){
        
                CL = new JButton("Clause");
		CL.setBounds(10, 144, 100, 23);
		CL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		CL.setLocation(45, 440);
		add(CL);
                CL.setVisible(true);
                
                SubCl = new JButton("Sub Clause");
		SubCl.setBounds(10, 144, 100, 23);
		SubCl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		SubCl.setLocation(150, 440);
		add(SubCl);
                SubCl.setVisible(true);
                
                Noun = new JButton("Noun");
		Noun.setBounds(10, 144, 100, 23);
		Noun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		Noun.setLocation(255, 440);
		add(Noun);
                Noun.setVisible(true);
                
                NP = new JButton("Noun Phrase");
		NP.setBounds(10, 144, 120, 23);
		NP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		NP.setLocation(360, 440);
		add(NP);
                NP.setVisible(true);
                
                Adj = new JButton("Adjective");
		Adj.setBounds(10, 144, 120, 23);
		Adj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		Adj.setLocation(485, 440);
		add(Adj);
                Adj.setVisible(true);
                
                AdjP = new JButton("Adjective Phrase");
		AdjP.setBounds(10, 144, 160, 23);
		AdjP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		AdjP.setLocation(45, 470);
		add(AdjP);
                AdjP.setVisible(true);
                
                Verb = new JButton("Verb");
		Verb.setBounds(10, 144, 100, 23);
		Verb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		Verb.setLocation(210, 470);
		add(Verb);
                Verb.setVisible(true);
                
                VP = new JButton("Verb Phrase");
		VP.setBounds(10, 144, 120, 23);
		VP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//insert functions here
                        }
		});
		VP.setLocation(315, 470);
		add(VP);
                VP.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
      
       public class Listener implements MouseListener {


       
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) throws NullPointerException {
            
            if(e.getSource() == save){
          
            
            }
                
               
                 
        }
       
        @Override
        public void mouseReleased(MouseEvent e) {
      
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == save) {
                save.setIcon(saveIcon[1]);
            } else if (e.getSource() == load) {
                load.setIcon(loadIcon[1]);
            } else if (e.getSource() == generate) {
                generate.setIcon(generateIcon[1]);
            } else if (e.getSource() == addButton) {
                addButton.setIcon(addButtonIcon[1]);
            }
            
        }
          @Override
        public void mouseExited(MouseEvent e) {
          if (e.getSource() == save) {
                save.setIcon(saveIcon[0]);
              
            } else if (e.getSource() == load) {
                load.setIcon(loadIcon[0]);
            } else if (e.getSource() == generate) {
                generate.setIcon(generateIcon[0]);
            } else if (e.getSource() == addButton) {
                addButton.setIcon(addButtonIcon[0]);
            }
        }
        

        
    }
    
   

}
