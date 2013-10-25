package ontology.controller;

import grammar.model.Constituent;

import java.util.List;

import javax.swing.JFrame;

import ontology.model.Concept;
import ontology.model.ConceptDAO;
import ontology.model.Tag;
import ontology.view.AddConceptDialog;
import ontology.view.OntologyList;
import commons.dao.DAOFactory;
import commons.main.MainFrame;

public class OntologyManager {
    Constituent noun;
    OntologyList panel;
    
    public static void main(String[] args) {
        new OntologyManager().testCase();
    }
    
    public void testCase() {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new OntologyList();
        frame.setPanel(panel);
    }
    

    

}
