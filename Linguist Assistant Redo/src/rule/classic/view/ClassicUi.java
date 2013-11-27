package rule.classic.view;

import javax.swing.JPanel;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;
import rule.classic.InputCons;
import rule.classic.OutputCons;
import semantics.model.Constituent;
import semantics.view.BlockListener;
import semantics.view.BlocksPanel;
import semantics.view.ButtonPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassicUi extends JPanel {
    private BlocksPanel input;
    private BlocksPanel output;
    private InputCons inputRoot;
    private OutputCons outputRoot;
    private InputDetailsPanel inputDetails;
    private OutputDetailsPanel outputDetails;
    private JButton btnCopyStructure;

    public static void main(String[] args) {
        new MainFrame().setPanel(new ClassicUi());
    }
    
    public ClassicUi() {
        input = new BlocksPanel();
        inputDetails = new InputDetailsPanel();
        ButtonPanel buttons = new ButtonPanel();
        output = new BlocksPanel();
        outputDetails = new OutputDetailsPanel();
        
        setLayout(new MigLayout("", "[][][]", "[][][]"));
        add(input, "cell 0 0");
        add(inputDetails, "cell 2 0");
        add(buttons, "cell 0 1");
        add(output, "cell 0 2");
        
        btnCopyStructure = new JButton("Copy structure");
       
        add(btnCopyStructure, "cell 1 2");
        add(outputDetails, "cell 2 2");

    }

    public InputDetailsPanel getInputDetails() {
        return inputDetails;
    }
    
    public void refresh() {
        System.out.println("refreshing");
        input.updateRoot(inputRoot);
        output.updateRoot(outputRoot);
    }
    
    public void addInputBlocksListener(BlockListener listener) {
        input.addBlockListener(listener);
    }
    
    public void addOutputBlocksListener(BlockListener listener) {
        output.addBlockListener(listener);
    }
    
    public void addCopyListener(ActionListener listener)
    {
    	 btnCopyStructure.addActionListener(listener);
    }
    
    public void setInputRoot(InputCons root) {
        inputRoot = root;
    }
    public InputCons getInputRoot(){
    	return inputRoot;
    }
    public void setOutputRoot(OutputCons root) {
        outputRoot = root;
    }
    
    public void setCons(InputCons cons) {
        inputDetails.setConstituent(cons);
    }
    
    public void setCons(OutputCons cons) {
        outputDetails.setConstituent(cons);
    }
}
