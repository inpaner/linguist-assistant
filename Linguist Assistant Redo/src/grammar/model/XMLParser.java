package grammar.model;

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

public class XMLParser {
    private final String FILENAME = "data/example-new.xml";
    
    public static void main(String argv[]) {
        XMLParser parser = new XMLParser();
       // Constituent root = parser.read(FILENAME);
        //parser.writeXML(root);
        
    }

    public Constituent read(String filename) {
        Constituent root = new Constituent(null);
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            Node rootNode = doc.getFirstChild();
            NodeList childNodes = rootNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                switch (node.getNodeName()) {
                    case "metadata": parseMetadata(node);   
                                     break;
                    case "subcons":  parseRootSubcons(node, root);  
                    default:         break;
                }
            }
        }    
        catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return root;
    }
    
    private void parseMetadata(Node metaNode) {
    }
        
    private void parseRootSubcons(Node subconsNode, Constituent root) {
        NodeList childNodes = subconsNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            switch (childNode.getNodeName()) {
            case "const":    Constituent constituent = parseConst(childNode, null);
                             root.addChild(constituent);   
                             break;
            
            default:         break;
            
            }
        }
    }
    
    private Constituent parseConst(Node constNode, Constituent parent) {
        Constituent constituent = new Constituent(parent);
        NodeList childNodes = constNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            switch (childNode.getNodeName()) {
            case "label":    constituent.setLabel(childNode.getTextContent());
                             break;
            
            case "subcons":  parseSubcons(childNode, constituent);
                             break;
            
            case "features": parseFeatures(childNode, constituent);
                             break;
            
            case "concept":  parseConcept(childNode, constituent);
                             break;
                             
            case "word":	 constituent.setTranslation(new Translation(childNode.getTextContent()));
							 break;
                                                       
            case "gloss": 	 constituent.setTranslation(new Translation(childNode.getTextContent()));
            				 break;
            default:         break;
            
            }
        }
         
        return constituent;
    }
    
    private void parseSubcons(Node subconsNode, Constituent parent) {
        NodeList constList = subconsNode.getChildNodes();
        for (int i = 0; i < constList.getLength(); i++) {
            Node childNode = constList.item(i);
            
            switch (childNode.getNodeName()) {
            case "const":   Constituent child = parseConst(childNode, parent);
                            parent.addChild(child);
                            break;
            
            default:        break;
            
            }
        }
    }
    
    private void parseFeatures(Node featuresNode, Constituent parent) {
        NodeList featureList = featuresNode.getChildNodes();
        for (int i = 0; i < featureList.getLength(); i++) {
            Node childNode = featureList.item(i);
            
            switch (childNode.getNodeName()) {
            case "feature": Feature feature = parseFeature(childNode, parent);
                            parent.addFeature(feature);
                            break;
            
            default:        break;
            
            }
        }
    }
    
    private Feature parseFeature(Node featureNode, Constituent parent) {
        NodeList childNodes = featureNode.getChildNodes();
        String name = "";
        String value = "";
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            
            switch (childNode.getNodeName()) {
            case "name":    name = childNode.getTextContent();
                            break;
            
            case "value":   value = childNode.getTextContent();
                            break;
                            
            default:        break;
            
            }
        }
        
        Feature feature = new Feature(name, value, parent);
        return feature;
    }
    
    private void parseConcept(Node conceptNode, Constituent parent) {
        String name = conceptNode.getTextContent();
        Concept concept = new Concept(name, parent);
        parent.setConcept(concept);
    }
    
    public void myWrite(Root root) {
        
        try {
            Constituent con = root.getConstituents().get(0);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder;
            dbBuilder = dbFactory.newDocumentBuilder();
            Document xml = dbBuilder.newDocument();
            
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        
    }
    
    public void writeXML(String filename, Constituent root){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document xml = dbBuilder.newDocument();
        
            Element rootElement = xml.createElement("root");
            xml.appendChild(rootElement);

                         
            Element meta = xml.createElement("metadata");
            rootElement.appendChild(meta);

            Element subCons = xml.createElement("subcons");
            rootElement.appendChild(subCons);
            
            for (Constituent constituent : root.getChildren()) {
                write(constituent, xml, subCons);
            }
            
            TransformerFactory tmFactory = TransformerFactory.newInstance();
            Transformer transformer = tmFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        
        }
        catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
        }
        
        
            
    }
    
    public void write(Constituent constituent, Document xml, Element parent){
        
        Element mainElement = xml.createElement("const");
        parent.appendChild(mainElement);
        
        Element label = xml.createElement("label");
        label.appendChild(xml.createTextNode(constituent.getLabel()));
        mainElement.appendChild(label);
        
        if (constituent.hasFeatures()) {
            Element feat = xml.createElement("features");
            mainElement.appendChild(feat);
                
            for(int i = 0; i < constituent.getFeatures().size(); i++) {
                Element feature = xml.createElement("feature");
                feat.appendChild(feature);
                
                Element name = xml.createElement("name");
                name.appendChild(xml.createTextNode(constituent.getFeatures().get(i).getName()));
                feature.appendChild(name);
                
                Element value = xml.createElement("value");
                value.appendChild(xml.createTextNode(constituent.getFeatures().get(i).getValue()));
                feature.appendChild(value);
            }
        }
        
        if (constituent.hasConcept()) {
            Element concept = xml.createElement("concept");
            concept.appendChild(xml.createTextNode(constituent.getConcept().getStem()));
            mainElement.appendChild(concept);
                   
            Element word = xml.createElement("word");
            word.appendChild(xml.createTextNode(constituent.getConcept().getWord()));
            mainElement.appendChild(word);
            		
            // Element gloss = xml.createElement("gloss");
            // gloss.appendChild(xml.createTextNode(constituent.getConcept().getGloss/getWord()));
            // mainElement.appendChild(gloss);
        }
        
        if(constituent.hasChildren()){
            Element child = xml.createElement("subcons");
            mainElement.appendChild(child);
            
            for(int i = 0; i < constituent.getChildren().size(); i++){
                write(constituent.getChildren().get(i), xml, child);
            
            }
        }
    }

}