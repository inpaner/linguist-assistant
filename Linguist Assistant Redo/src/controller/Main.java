package controller;

import java.awt.Dimension;

import javax.swing.JPanel;

import model.Constituent;
import model.Root;
import model.XMLParser;
import uitextgen.UITEXTGEN;
import view.MainFrame;
import view.SemanticDisplay;

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
    	
        XMLParser parser = new XMLParser();

        //parser.writeXML(root);
        
    }

}
