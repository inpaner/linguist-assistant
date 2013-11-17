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

import rule.model.input.*;
import rule.model.output.*;

public class RuleParser {
	
	public void writeXML(String filename, Rule rule){
		
		try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document xml = dbBuilder.newDocument();
        
            Element rootElement = xml.createElement("rules");
            xml.appendChild(rootElement);

                         
            Element meta = xml.createElement("metadata");
            // sample metadata
            meta.setAttribute("name", "Rule 1");
            meta.setAttribute("description", "Test");
            meta.setAttribute("comment", "None");
            meta.setAttribute("enabled", "True");
            meta.setAttribute("type", "Simple");
            rootElement.appendChild(meta);
            
            if(rule.getInput() != null){
            	Element input = xml.createElement("input");
            	rootElement.appendChild(input);
            	if(rule.getInput() instanceof And){
            		And casted = (And) rule.getInput();
            		input.setAttribute("name", "And");
            		Boolean x = casted.isOptional();
            		input.setAttribute("optional", x.toString());
            	}
            	else if(rule.getInput() instanceof Or){
            		Or casted = (Or) rule.getInput();
            		input.setAttribute("name", "Or");
            		Boolean x = casted.isOptional();
            		input.setAttribute("optional", x.toString());
            	}
            	else if(rule.getInput() instanceof HasCategory){
            		HasCategory casted = (HasCategory) rule.getInput();
            		input.setAttribute("name", "HasCategory");
            		input.setAttribute("category", casted.getCategory().getName());
            		Boolean x = casted.isOptional();
            		input.setAttribute("optional", x.toString());
            	}
            	else if(rule.getInput() instanceof HasChild){
            		HasChild casted = (HasChild) rule.getInput();
            		input.setAttribute("name", "HasChild");
            		Boolean x = casted.isOptional();
            		input.setAttribute("optional", x.toString());
            		input.setAttribute("var", casted.getKey());
            	}
            	else if(rule.getInput() instanceof HasConcept){
            		HasConcept casted = (HasConcept) rule.getInput();
            		input.setAttribute("name", "HasConcept");
            	}
            	else if(rule.getInput() instanceof HasFeature){
            		HasFeature casted = (HasFeature) rule.getInput();
            		input.setAttribute("name", "HasFeature");
            	}
            }
            
            if(rule.getOutputs().get(0) != null){
            	Element outputs = xml.createElement("outputs");
            	rootElement.appendChild(outputs);
            
            	for(int i = 0; i < rule.getOutputs().size(); i++){
            		Element output = xml.createElement("output");
            		if(rule.getOutputs().get(i) instanceof SetTranslation){
            			SetTranslation casted = (SetTranslation) rule.getOutputs().get(i);
            			output.setAttribute("name", "SetTranslation");
            			output.setAttribute("language", casted.getLanguage().getName());
            			output.setAttribute("var", casted.getKey());
            		}
            		else if(rule.getOutputs().get(i) instanceof ForceTranslation){
            			ForceTranslation casted = (ForceTranslation) rule.getOutputs().get(i);
            			output.setAttribute("name", "ForceTranslation");
            			output.setAttribute("translation", casted.getTranslation());
            			output.setAttribute("var", casted.getKey());
            		}
            		else if(rule.getOutputs().get(i) instanceof SetAffix){
            			SetAffix casted = (SetAffix) rule.getOutputs().get(i);
            			output.setAttribute("name", "SetAffix");
            			output.setAttribute("Affix", casted.getAffix().toString());
            			output.setAttribute("value", casted.getValue());
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
