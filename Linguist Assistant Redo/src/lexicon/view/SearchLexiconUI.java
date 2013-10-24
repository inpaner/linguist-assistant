package lexicon.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchLexiconUI extends JFrame{
	
	public JLabel sLabel;
	public JTextField search;
	public JButton ok;
	public JButton cancel;
	
	public SearchLexiconUI(){
		
		initialize();
		setBounds();
		setFrame();
		addToFrame();
		buttonFunctions();
	}
	
	public void initialize(){
		
	sLabel = new JLabel("Enter the text you want to search for: ");
	search = new JTextField();
	ok = new JButton("OK");
	cancel = new JButton("Cancel");
	
	}
	
	public void setBounds(){
		
		sLabel.setBounds(15, 25, 230, 20);
		search.setBounds(239, 25, 230, 20);
		ok.setBounds(180,60, 60,20);
		cancel.setBounds(245,60,80,20);
	}
	
	public void setFrame(){
		
		this.setSize(500, 150);
        getContentPane().setLayout(null);
        this.setVisible(true);
        this.setLocation(400, 300);
	}
	
	public void addToFrame(){
		
		this.add(sLabel);
		this.add(search);
		this.add(ok);
		this.add(cancel);
		
		sLabel.setVisible(true);
		search.setVisible(true);
		ok.setVisible(true);
		cancel.setVisible(true);
	}
	
	public void buttonFunctions(){
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(search.getText().equals("")){
					search.setBackground(Color.red);
				}
				else{
					search.setBackground(Color.white);
				}

			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.exit(0); 
				dispose();
			}
		});
	}
	
	public static void main(String[] args){
		
		SearchLexiconUI slui = new SearchLexiconUI();
	}
}
