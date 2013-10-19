package controller;

import grammar.model.Constituent;
import grammar.model.Root;
import grammar.model.XMLParser;

import java.awt.Dimension;

import javax.swing.JPanel;

import commons.view.MainFrame;

import view.SemanticEditorPanel;

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
