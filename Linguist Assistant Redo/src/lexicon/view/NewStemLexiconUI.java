package lexicon.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewStemLexiconUI extends JFrame{
	
	private JLabel nstem;
	private JLabel modifier;
	private JLabel tag;
	private JLabel gender;
	private JLabel articles;
	private JLabel countable;
	private JLabel ctype;
	private JLabel plural;
	private JLabel gloss;
	private JLabel comment;
	private JLabel sentence;
	
	private JTextField nstemField;
	private JTextField modifierField;
	private JTextField pluralField;
	private JTextField glossField;
	private JTextField commentField;
	private JTextField sentenceField;
	
	JComboBox tagCbox = new JComboBox();
	JComboBox articlesCbox = new JComboBox();
	JComboBox countableCbox = new JComboBox();
	JComboBox ctypeCbox = new JComboBox();
	JComboBox genderCbox = new JComboBox();
	
	
	private JButton add;
	
	public NewStemLexiconUI(){
		
		initialize();
		setBounds();
		setFrame();
		addToFrame();
		buttonFunctions();
	}
	
	public void initialize(){
		
		nstem = new JLabel("New Stem");
		modifier = new JLabel("Modifier");
		tag = new JLabel("Tag");
		gender = new JLabel("Gender");
		articles = new JLabel("Takes articles");
		countable = new JLabel("Countable");
		ctype = new JLabel("Relative Clause Type");
		plural = new JLabel("Plural");
		gloss = new JLabel("Gloss");
		comment = new JLabel("Comment");
		sentence = new JLabel("Sample Sentence");
		
		nstemField = new JTextField();
		modifierField = new JTextField();
		pluralField = new JTextField();
		glossField  = new JTextField();
		commentField  = new JTextField();
		sentenceField  = new JTextField();
		
		add = new JButton("Add Stem");
	}
	
	public void setBounds(){
		
		nstem.setBounds(20,25, 150, 20);
		modifier.setBounds(20,45, 150, 20);
		tag.setBounds(20,65, 150, 20);
		gender.setBounds(20,85, 150, 20);
		articles.setBounds(20,105, 150, 20);
		countable.setBounds(20,125, 150, 20);
		ctype.setBounds(20,145, 150, 20);
		plural.setBounds(20,165, 150, 20);
		gloss.setBounds(20,185, 150, 20);
		comment.setBounds(20,205, 150, 20);
		sentence.setBounds(20,225, 150, 20);
		
		nstemField.setBounds(170,25, 150, 20);
		modifierField.setBounds(170,45, 150, 20);
		pluralField.setBounds(170,165, 150, 20);
		glossField.setBounds(170,185, 150, 20);
		commentField.setBounds(170,205, 150, 20);
		sentenceField.setBounds(170,225, 150, 20);
		
		tagCbox.setBounds(170,65, 150, 20);
		articlesCbox.setBounds(170,105, 150, 20);
		countableCbox.setBounds(170,125, 150, 20);
		ctypeCbox.setBounds(170,145, 150, 20);
		genderCbox.setBounds(170,85, 150, 20);
		
		add.setBounds(100,270,150,20);
		
	}
	
	public void setFrame(){
		
		this.setSize(350, 350);
        getContentPane().setLayout(null);
        this.setVisible(true);
        this.setLocation(600, 200);
        this.setResizable(false);
	}
	
	public void addToFrame(){
		
		this.add(nstem);
		this.add(modifier);
		this.add(tag);
		this.add(gender);
		this.add(articles);
		this.add(countable);
		this.add(ctype);
		this.add(plural);
		this.add(gloss);
		this.add(comment);
		this.add(sentence);
		this.add(nstemField);
		this.add(modifierField);
		this.add(pluralField);
		this.add(glossField);
		this.add(commentField);
		this.add(sentenceField);
		this.add(tagCbox);
		this.add(articlesCbox);
		this.add(countableCbox);
		this.add(ctypeCbox);
		this.add(genderCbox);
		this.add(add);
		
		nstem.setVisible(true);
		modifier.setVisible(true);
		tag.setVisible(true);
		gender.setVisible(true);
		articles.setVisible(true);
		countable.setVisible(true);
		ctype.setVisible(true);
		plural.setVisible(true);
		gloss.setVisible(true);
		comment.setVisible(true);
		sentence.setVisible(true);
		nstemField.setVisible(true);
		modifierField.setVisible(true);
		pluralField.setVisible(true);
		glossField.setVisible(true);
		commentField.setVisible(true);
		sentenceField.setVisible(true);
		tagCbox.setVisible(true);
		articlesCbox.setVisible(true);
		countableCbox.setVisible(true);
		ctypeCbox.setVisible(true);
		genderCbox.setVisible(true);
		add.setVisible(true);
		
	}
	
	public void buttonFunctions(){
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nstemField.getText().equals("")){
					nstemField.setBackground(Color.red);
				}
				else{
					nstemField.setBackground(Color.white);
				}
				
				if(modifierField.getText().equals("")){
					modifierField.setBackground(Color.red);
				}
				else{
					modifierField.setBackground(Color.white);
				}
				
				if(pluralField.getText().equals("")){
					pluralField.setBackground(Color.red);
				}
				else{
					pluralField.setBackground(Color.white);
				}
				
				if(glossField.getText().equals("")){
					glossField.setBackground(Color.red);
				}
				else{
					glossField.setBackground(Color.white);
				}
				
				if(commentField.getText().equals("")){
					commentField.setBackground(Color.red);
				}
				else{
					commentField.setBackground(Color.white);
				}
				
				if(sentenceField.getText().equals("")){
					sentenceField.setBackground(Color.red);
				}
				else{
					sentenceField.setBackground(Color.white);
				}
				

			}
		});
	}
	public static void main(String[] args){
		
		NewStemLexiconUI nslui = new NewStemLexiconUI();
	}

}
