package rule.classic;

import java.util.List;

import grammar.controller.SelectConstituent;

import javax.swing.JOptionPane;

import commons.main.MainFrame;
import rule.classic.view.ClassicUi;
import rule.classic.view.InputDetailsPanel;
import semantics.model.Constituent;
import semantics.view.BlockListener;
import semantics.view.BlocksPanel;

public class ClassicRuleMaker {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        new ClassicRuleMaker(frame);
    }
    
    private ClassicUi view;
    private int cons = 0;

    public ClassicRuleMaker(MainFrame frame) {
        view = new ClassicUi();
        frame.setPanel(view);
        InputDetailsPanel inputView = view.getInputDetails();
        InputDetailsMgr inputMgr = new InputDetailsMgr(inputView);
        inputMgr.addListener(new DoneListener());
        view.addInputBlocksListener(new InputBlockListener());
        view.addOutputBlocksListener(new OutputBlockListener());
        InputCons inputRoot = new InputCons();
        OutputCons outputRoot = new OutputCons();
        view.setInputRoot(inputRoot);
        view.setOutputRoot(outputRoot);
        
        view.refresh();
    }
    
    private OutputCons getOutput(InputCons cons) {
        OutputCons result = new OutputCons();
        result.setCategory(cons.getCategory());
        result.key = cons.key;
        result.setConcept(cons.getConcept());
        
        List<Constituent> outputChildren = result.getChildren();
        
        for (Constituent inputChild : cons.getChildren()) {
            OutputCons outputChild = getOutput((InputCons) inputChild);
            outputChildren.add(outputChild);
        }
        
        return result;
    }
    
    
    private class InputBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {
            view.setCons((InputCons) constituent);
        }

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {
            destination.moveChild(dropped, index);
            view.refresh();
        }

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {
            InputCons toAdd = new InputCons();
            toAdd.setCategory(dropped.getCategory());
            toAdd.key = String.valueOf(cons);
            cons++;
            destination.moveChild(toAdd, index);
            view.refresh();
        }

        @Override
        public void rightClick(Constituent category) {
            int choice = JOptionPane.showConfirmDialog(view, "Delete this constituent?", "Confirm Delete", 0); 
            if(choice == JOptionPane.YES_OPTION) {
                if (category.getParent() != null) {
                    category.getParent().getChildren().remove(category);
                    category.setParent(null);
                    view.refresh();
                }
            }
        }
    }

    
    private class OutputBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {
            view.setCons((OutputCons) constituent);
        }

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {
            Constituent oldParent = dropped.getParent();
            int oldIndex = oldParent.getChildren().indexOf(dropped);
            destination.moveChild(dropped, index);
            
/*            
            // COPY
            if (!oldParent.equals(destination)) {
                oldParent.getChildren().add(oldIndex, dropped);
            }
*/
            view.refresh();
        }

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {
            OutputCons toAdd = new OutputCons();
            toAdd.setCategory(dropped.getCategory());
            toAdd.key = String.valueOf(cons);
            cons++;
            destination.moveChild(toAdd, index);
            view.refresh();
        }

        @Override
        public void rightClick(Constituent category) {
            int choice = JOptionPane.showConfirmDialog(view, "Delete this constituent?", "Confirm Delete", 0); 
            if(choice == JOptionPane.YES_OPTION) {
                if (category.getParent() != null) {
                    category.getParent().getChildren().remove(category);
                    category.setParent(null);
                    view.refresh();
                }
            }
        }
    }
    

    
    private class DoneListener implements InputDetailsMgr.Listener {
        @Override
        public void done() {
            view.refresh();
        }
    }
}
