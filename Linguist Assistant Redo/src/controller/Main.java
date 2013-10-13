package controller;

import java.awt.Dimension;

import javax.swing.JPanel;

import model.Constituent;
import model.Root;
import model.XMLParser;
import uitextgen.UITEXTGEN;
import view.MainFrame;
import view.SemanticDisplay;
import display.Block;

public class Main {

	/**
	 * @param args
	 */
    public static void main2(String[] args) {
        MainFrame frame = new MainFrame();
        //JPanel panel = new SemanticDisplay();
        //frame.setPanel(panel);
    }
    
	public static void main(String argv[]) {
    	UITEXTGEN ui = null;
    	Dimension size;
		try {
			ui = new UITEXTGEN();
			System.out.println("Hi");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	Block b;
    	int x=10;
    	int y=22;
        XMLParser parser = new XMLParser();
        Root root = parser.read();
        for(Constituent c : root.getConstituents())
        {
        	
        	b=new Block(c,x,y, 0, ui);
        	if(ui!=null)
        	{
        		//
        	ui.add(b);
        	}
        	//size=new Dimension(300,300);
        	//b.setPreferredSize(size);
        	System.out.println("Block added "+b.getColorIndex());
        	//b.setVisible(true);
        	ui.repaint();
        	x+=120;
        }
        parser.writeXML(root);
        
    }

}
