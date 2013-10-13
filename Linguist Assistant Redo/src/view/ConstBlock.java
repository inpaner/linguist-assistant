package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ConstBlock extends Box {
    private Constituent constituent;
    private ArrayList<ConstBlock> children;
    private ConstBlock parent;
    private JLabel label;
    private Box contentBox;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        Constituent con = new Constituent(null);
        Constituent con2 = new Constituent(null);
        con.addChild(con2);
        con.setLabel("Noun");
        con2.setLabel("child");
        panel.add(new ConstBlock(con));
        frame.setPanel(panel);
    }
    
    public ConstBlock(Constituent constituent) {
        this(constituent, null);
    }
    
    public ConstBlock(final Constituent constituent, ConstBlock parent) {
        super(BoxLayout.X_AXIS);
        children = new ArrayList<>();
        this.constituent = constituent;
        this.parent = parent;
        
        Border lineEdge = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(lineEdge);
        
        contentBox = new Box(BoxLayout.X_AXIS);
        Border paneEdge = BorderFactory.createEmptyBorder(10,10,10,10);
        contentBox.setBorder(paneEdge);
        add(contentBox);
        
        label = new JLabel(constituent.getLabel());
        contentBox.add(label);
        
        addMouseListener(clickedBox());
        for (Constituent childConst : constituent.getChildren()) {
            ConstBlock child = new ConstBlock(childConst);
            children.add(child);
            contentBox.add(child);
        }
        
        
        
    }
    private boolean showChildren = false;
    
    private void toggleChildren() {
        for (ConstBlock child : children) {
            if (showChildren) {
                child.setVisible(true);
            }
            else {
                child.setVisible(false);
            }
        }
        
        showChildren = !showChildren;
    }
    
    private MouseListener clickedBox() {
        return new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    toggleChildren();
                }
                
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            

        };
    }
    
    
}
