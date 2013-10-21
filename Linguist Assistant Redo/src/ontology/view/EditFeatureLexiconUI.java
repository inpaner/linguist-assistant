package ontology.view;

import grammar.model.Constituent;
import grammar.model.Feature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class EditFeatureLexiconUI extends JFrame{
	
	private JLabel label; 
	private JLabel comment;
	private JTextField commentbox;
	private JButton ok;
	private JButton cancel;
	private JTable table;
	DefaultTableModel model;
	JComboBox feature = new JComboBox();
	Constituent con;
	public EditFeatureLexiconUI(Constituent c){
		con=c;
		initialize();
		setBounds();
		setFrame();
		addToFrame();
		//System.out.println("C is"+ c.getLabel());
		
		//System.out.println("Con is"+ con.getLabel());
	}
	public void saveFeature(){
		ArrayList<String>features=new ArrayList<String>();
		String value;
		System.out.println("Saving");
		for(int i=0;i<model.getRowCount();i++)
		{
			System.out.println(model.getValueAt(i, 0));
			if(model.getValueAt(i, 0)!=null)
			{
			value=model.getValueAt(i, 0).toString();
			
				features.add(value);
			}
			else System.out.println("NULL VALUE");
		}
		Feature f=new Feature(feature.getSelectedItem().toString());
		//TOFO: save feature somehow
	}
	public void initialize(){
		
		label = new JLabel("Which feature do you want to edit?");
		comment = new JLabel("Comment: ");
		commentbox = new JTextField();
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFeature();
			}
		});
		cancel = new JButton("Cancel");
		table = new JTable();
		feature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillTable(feature.getSelectedItem().toString());
			}
		});
		model=
		new DefaultTableModel(
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
			);
		table.setModel(model);
		fillComboBox();
	}
	public void fillComboBox()
	{
		if(con!=null)
		{
			List<Feature>features=con.getFeatures();
		for(Feature f: features)
	       {
			//System.out.println("Feature added to cbx: "+f.getName());
			feature.addItem(f.getName());
	       }
		}
		//else System.out.println("Con is NULL");
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
	public void fillTable(String featureName)
	{
		for(Feature f: con.getFeatures())
		{
			if(f.getName()==featureName)
			{
				int i=0;
				for(String s: f.getPossibleValues())
				{
					if(i>=model.getRowCount())
					{
						model.addRow(new Object[]{});
					}
					model.setValueAt(s,i,0);
					i++;
				}
			}
		}
	}
	public void addToFrame(){
		
		getContentPane().add(label);
		getContentPane().add(feature);
		getContentPane().add(comment);
		getContentPane().add(commentbox);
		getContentPane().add(ok);
		getContentPane().add(cancel);
		
		getContentPane().add(table);
		
		
		
		label.setVisible(true);
		feature.setVisible(true);
		comment.setVisible(true);
		commentbox.setVisible(true);
		ok.setVisible(true);
		cancel.setVisible(true);
		
		
		
	}
	
	public static void main(String[] args){
		
		//EditFeatureLexiconUI eflui = new EditFeatureLexiconUI();
	}

}
