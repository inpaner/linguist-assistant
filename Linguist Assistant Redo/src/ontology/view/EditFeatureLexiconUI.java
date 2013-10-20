package ontology.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditFeatureLexiconUI extends JFrame{
	
	private JLabel label; 
	private JLabel comment;
	private JTextField commentbox;
	private JButton ok;
	private JButton cancel;
	private JTable table;
	
	JComboBox feature = new JComboBox();
	
	public EditFeatureLexiconUI(){
		
		initialize();
		setBounds();
		setFrame();
		addToFrame();
	}
	
	public void initialize(){
		
		label = new JLabel("Which feature do you want to edit?");
		comment = new JLabel("Comment: ");
		commentbox = new JTextField();
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
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
	}
	

	
	public void setBounds(){
		
		label.setBounds(60, 25, 230, 20);
		feature.setBounds(280, 24, 200, 20);
		comment.setBounds(30,280,200,20);
		commentbox.setBounds(30, 305,540,200);
		ok.setBounds(200,530,80,20);
		cancel.setBounds(290, 530,100,20);
		table.setBounds(30,60,500,200);
		
		
	}
	
	public void setFrame(){
		
		this.setSize(600, 600);
        getContentPane().setLayout(null);
        this.setVisible(true);
        this.setLocation(400, 300);
        this.setResizable(false);
	}
	
	public void addToFrame(){
		
		this.add(label);
		this.add(feature);
		this.add(comment);
		this.add(commentbox);
		this.add(ok);
		this.add(cancel);
		
		this.add(table);
		
		
		
		label.setVisible(true);
		feature.setVisible(true);
		comment.setVisible(true);
		commentbox.setVisible(true);
		ok.setVisible(true);
		cancel.setVisible(true);
		
		
		
	}
	
	public static void main(String[] args){
		
		EditFeatureLexiconUI eflui = new EditFeatureLexiconUI();
	}

}
