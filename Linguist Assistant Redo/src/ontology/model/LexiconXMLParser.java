package ontology.model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ontology.model.Concept;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LexiconXMLParser {
    private final String FILENAME = "data/lexicon.xml";
    
    public static void main(String argv[]) {
        LexiconXMLParser parser = new LexiconXMLParser();
       // Constituent root = parser.read(FILENAME);
        //parser.writeXML(root);
        
    }

   

}