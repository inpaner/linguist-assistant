package ontology.model;

import lexicon.model.Entry;
import grammar.model.Feature;

public class POS {
	
	private String label;
	private Entry[] entry;
	private Feature[] feature;
	private Feature[] forms;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Entry[] getEntry() {
		return entry;
	}
	public void setEntry(Entry[] entry) {
		this.entry = entry;
	}
	public Feature[] getFeature() {
		return feature;
	}
	public void setFeature(Feature[] feature) {
		this.feature = feature;
	}
	public Feature[] getForms() {
		return forms;
	}
	public void setForms(Feature[] forms) {
		this.forms = forms;
	}
}
