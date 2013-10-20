package ontology.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NewFeatureLexiconUI extends JFrame {
	
	public JLabel label;
	public JTextField feature;
	public JButton addfeature;
	private JTable table;
	
	public NewFeatureLexiconUI(){
		
		initialize();
		setBounds();
		setFrame();
		addToFrame();
		buttonFunctions();
	}
	
	public void initialize(){
		label = new JLabel("Name of the new feature");
		feature = new JTextField();
		addfeature = new JButton("Add Feature");
	
	}
	public void setBounds(){
		label.setBounds(130,25,180,20);
		feature.setBounds(285,24,150,20);
		addfeature.setBounds(190,620,180,20);
		
	}
	public void setFrame(){
		this.setSize(600, 700);
        getContentPane().setLayout(null);
        this.setVisible(true);
        this.setLocation(400, 20);
	}
	public void addToFrame(){
		getContentPane().add(label);
		getContentPane().add(feature);
		getContentPane().add(addfeature);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
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
			},
			new String[] {
				"Value name", "Character"
			}
		));
		table.setBounds(10, 53, 564, 556);
		
		getContentPane().add(table);
		
		label.setVisible(true);
		feature.setVisible(true);
		addfeature.setVisible(true);
	}
	
	public void buttonFunctions(){
		
		addfeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(feature.getText().equals("")){
					feature.setBackground(Color.red);
				}
				else{
					feature.setBackground(Color.white);
				}

			}
		});
	}
	
	public static void main(String[] args){
		
		NewFeatureLexiconUI nflui = new NewFeatureLexiconUI();
	}
}
