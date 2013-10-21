package ontology.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ontology.model.Form;

public class NewFormLexiconUI extends JFrame{
	
	private JLabel label;
	private JTextField formField;
	private JButton ok;
	private JButton cancel;
	
	public NewFormLexiconUI(){
		initialize();
		setFrame();
		setBounds();
		addToFrame();
		buttonFunctions();
	}
	
	public void initialize(){
		
		label = new JLabel("Enter the name of the new form");
		formField = new JTextField();
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		
	}
	
	public void setFrame(){
		this.setSize(400, 120);
        getContentPane().setLayout(null);
        this.setVisible(true);
        this.setLocation(400, 300);
        this.setResizable(false);
	}
	
	public void setBounds(){
		
		label.setBounds(14,25,180,20);
		formField.setBounds(204,25,180,20);
		ok.setBounds(120,60,70,20);
		cancel.setBounds(200,60,80,20);
	}
	
	public void addToFrame(){
		
		this.add(label);
		this.add(formField);
		this.add(ok);
		this.add(cancel);
		
		label.setVisible(true);
		formField.setVisible(true);
		ok.setVisible(true);
		cancel.setVisible(true);
	}
	
	public void buttonFunctions(){
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(formField.getText().equals("")){
					formField.setBackground(Color.red);
				}
				else{
					formField.setBackground(Color.white);
					saveForm(formField.getText());
				}

			}
		});
	}
	public void saveForm(String name)
	{
		Form f=new Form(name);
		f.saveToDB();
	}
	public static void main(String[] args){
		
		NewFormLexiconUI nflui = new NewFormLexiconUI();
	}
	

}
