package controller;

import grammar.model.Constituent;

import java.util.ArrayList;
import java.util.List;

import view.AddConstituentListener;
import view.AddConstituentPanel;
import view.GenericDialog;

public class AddConstituent {
    private List<AddConstituent.Listener> listeners;
    private Constituent destination;
    private int index;
    
    private AddConstituentPanel addConstituentPanel;
    private GenericDialog dialog;
    
    protected AddConstituent(Constituent toAdd, Constituent destination, int index) {
        this.destination = destination;
        this.index = index;
        listeners = new ArrayList<>();
        dialog = new GenericDialog();
        addConstituentPanel = new AddConstituentPanel(toAdd);
        addConstituentPanel.addListener(new AddConstituentListener_());
        dialog.setPanel(addConstituentPanel);
    }
    
    
    private class AddConstituentListener_ implements AddConstituentListener {
        @Override
        public void clickedOk(Constituent toAdd) {
            destination.moveChild(toAdd, index);
            dialog.closeDialog();
            for (AddConstituent.Listener listener : listeners) {
                listener.done();
            }
        }

        @Override
        public void clickedCancel() {
            dialog.closeDialog();
            for (AddConstituent.Listener listener : listeners) {
                listener.done();
            }
        }   
    }
    
    protected void addListener(AddConstituent.Listener listener) {
        listeners.add(listener);
    }
    
    public interface Listener {
        public abstract void done();
    }
}
