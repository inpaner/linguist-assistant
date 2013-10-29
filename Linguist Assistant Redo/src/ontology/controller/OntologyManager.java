package ontology.controller;

import grammar.model.Constituent;

import ontology.view.OntologyList;
import ontology.view.OntologyManagerUi;

import commons.main.MainFrame;

public class OntologyManager {
    private static OntologyManagerUi panel;
    
    public static void run(MainFrame frame) {
        if (panel == null) {
            panel = new OntologyManagerUi(frame);
        }
        frame.setPanel(panel);
    }
}
