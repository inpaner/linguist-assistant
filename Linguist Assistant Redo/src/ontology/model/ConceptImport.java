package ontology.model;

import grammar.model.Constituent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import commons.dao.DAOFactory;

public class ConceptImport {
    private final String FOLDER = "data/ontologies/";
    private final String FILENAME = "nouns.txt";
    
    public static void main(String[] args) {
        /*DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        Constituent c = Constituent.get("N");
        Concept concept = new Concept(c);
        concept.setStem("dog");
        concept.setGloss("animal that barks");
        
        dao.add(concept);*/
        new ConceptImport().parse();
    }
    
    void parse() {
        String parsed = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(FOLDER + FILENAME));
            String line = in.readLine();
            
            DAOFactory factory = DAOFactory.getInstance();
            ConceptDAO dao = new ConceptDAO(factory);
            Constituent constituent = Constituent.get("N");
            
            while (line != null) {
                if (!line.isEmpty()) {
                    Concept concept = parseLine(line, constituent);
                    parsed = concept.getStem();
                    dao.add(concept);
                    System.out.println("Added: " + parsed);
                }
                line = in.readLine();
            }
            System.out.println("Done.");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    Concept parseLine(String line, Constituent con) {
        Concept concept = new Concept(con);
        line = line.trim();
        line = line.replaceAll("\\s+", " ");
        
        String[] linesArr = line.split(" ");
        ArrayList<String> words = new ArrayList<>(Arrays.asList(linesArr));
        String stem = words.remove(0);
        StringBuilder sb = new StringBuilder();
        
        String sep = "";
        for (String word : words) {
            sb.append(sep).append(word);
            sep = " ";
        }
        
        String gloss = sb.toString();
        gloss = gloss.substring(1, gloss.length() - 1);
        
        concept.setStem(stem);
        concept.setGloss(gloss);
        return concept;
    }
}   
