package semantics.view;

import grammar.model.Category;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import semantics.model.Constituent;
import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

public class BlocksPanel extends JPanel {
    private static final long serialVersionUID = 7119799436798838106L;
    private static final String BlocksPanel = null;
    private List<BlockListener> listeners;
    private Constituent root;
    private Block rootBlock;
    private JScrollPane scrollPane;
    private JPanel innerPanel;
    
    public BlocksPanel() {
        initComponents();
        //addComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout());
        listeners = new ArrayList<BlockListener>();
    }
    
    private void addComponents() {
        innerPanel = new JPanel();
        innerPanel.add(rootBlock);
        scrollPane = new JScrollPane(innerPanel);
        add(scrollPane);
    }
    
    public void updateRoot(Constituent root) {
        this.root = root;
        rootBlock = new Block(root);
        rootBlock.addListeners(listeners);
        removeAll();
        addComponents();
        updateUI();
    }
    
    public void addBlockListener(BlockListener listener) {
        listeners.add(listener);
        if (rootBlock != null) {
            rootBlock.addBlockListener(listener);
        }
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
/*        Category con = new Category();
        Category con2 = new Category();
        Category con3 = new Category();
        con.setLabel("Noun");
        con2.setLabel("child 1");
        con3.setLabel("child 2");
        con.addChild(con2);
        con.addChild(con3);
        BlocksPanel panel = new BlocksPanel();
        panel.updateRoot(con);
        frame.setPanel(panel);*/
    }
}
