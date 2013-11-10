package rule.model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rule.model.output.Output;
import rule.model.output.SetTranslation;
import semantics.model.Constituent;

public class RuleParser {
	
	public void writeXML(String filename, Rule rule){
		
		try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document xml = dbBuilder.newDocument();
        
            Element rootElement = xml.createElement("rule");
            xml.appendChild(rootElement);

                         
            Element meta = xml.createElement("metadata");
            // sample metadata
            meta.setAttribute("name", "Rule 1");
            meta.setAttribute("description", "Test");
            meta.setAttribute("comment", "None");
            meta.setAttribute("enabled", "True");
            meta.setAttribute("type", "Simple");
            rootElement.appendChild(meta);
            
            if(rule.getOutputs().get(0) != null){
            	Element outputs = xml.createElement("outputs");
            	rootElement.appendChild(outputs);
            
            	for(int i = 0; i < rule.getOutputs().size(); i++){
            		Element output = xml.createElement("output");
            		if(rule.getOutputs().get(i) instanceof SetTranslation){
            			SetTranslation casted = (SetTranslation) rule.getOutputs().get(i);
            			output.setAttribute("name", "SetTranslation");
            			output.setAttribute("language", casted.getLanguage().getName());
            			output.setAttribute("key", casted.getKey());
            		}
            		outputs.appendChild(output);
            	}
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
	
}
