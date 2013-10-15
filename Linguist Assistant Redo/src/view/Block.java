package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Block extends Box {
    private static ArrayList<Color> colors;
    private DataFlavor blockFlavor = new DataFlavor(Block.class, Block.class.getSimpleName());
    
    private Constituent constituent;
    private int colorIndex;
    private ArrayList<Block> children;
    private ArrayList<Box> spacers;
    private Block parent;
    private JLabel nameLabel;
    private JLabel conceptLabel;
    private Box contentBox;
    private boolean showChildren;
    private ArrayList<BlockListener> blockListeners;
    
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        Constituent con = new Constituent(null);
        Constituent con2 = new Constituent(null);
        Constituent con3 = new Constituent(null);
        
        con.addChild(con2);
        con.addChild(con3);
        con.setLabel("Noun");
        con2.setLabel("child 1");
        con3.setLabel("child 2");
        
        panel.add(new Block(con));
        frame.setPanel(panel);
    }
    
    static {
        System.out.println("init colors");
        colors = new ArrayList<>();
        colors.add(new Color(255,0,0));
        colors.add(new Color(0,255,0));
        colors.add(new Color(0,0,255));
        colors.add(new Color(255,0,255));
        
    }
    
    public Block(Constituent constituent) {
        this(constituent, null, 0);
    }
    
    public Block(Constituent constituent, int colorIndex) {
        this(constituent, null, colorIndex);
    }
    
    public Block(final Constituent constituent, Block parent, int colorIndex) {
        super(BoxLayout.X_AXIS);
        
        children = new ArrayList<>();
        spacers = new ArrayList<>();
        blockListeners = new ArrayList<>();
        this.constituent = constituent;
        this.parent = parent;
        showChildren = false;

        this.colorIndex = colorIndex % colors.size();
        Border lineEdge = BorderFactory.createLineBorder(colors.get(colorIndex));
        setBorder(lineEdge);
        
        nameLabel = new JLabel(constituent.getLabel());
        conceptLabel = new JLabel();
        if(constituent.getConcept() != null) {
            conceptLabel.setText(constituent.getConcept().getName());
        } 
        else {
            conceptLabel.setText(" w i w");
        }
        
        JPanel textPanel = new JPanel();
        //textPanel.setLayout(new MigLayout());
        textPanel.add(nameLabel);
        textPanel.add(conceptLabel);
        
        Box textBox = Box.createVerticalBox();
        textBox.add(nameLabel);
        textBox.add(conceptLabel);
        
        contentBox = Box.createHorizontalBox();
        Border paneEdge = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentBox.setBorder(paneEdge);
        contentBox.add(textBox);
        contentBox.add(newSpacer());
        add(contentBox);
        
        addMouseListener(new ClickedBox());
        for (Constituent childConst : constituent.getChildren()) {
            addSubBlock(childConst, colorIndex + 1);
        }
        
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, new DragGestureListImp());
    }
    
    private Box newSpacer() {
        Box spacer = Box.createHorizontalBox();
        spacer.add(Box.createRigidArea(new Dimension(10, 30)));
        Border space = BorderFactory.createEmptyBorder(10, 5, 10, 5);
        //spacer.setBorder(space);
        spacers.add(spacer);
        spacer.setOpaque(true);
        new SpacerDropListener(spacer);
        return spacer;
    }
    
    private void toggleChildren() {
        for (Block child : children) {
            if (showChildren) {
                child.setVisible(true);
            } 
            else {
                child.setVisible(false);
            }
        }
        for (Box spacer : spacers) {
            if (showChildren) {
                spacer.setVisible(true);
            } 
            else {
                spacer.setVisible(false);
            }
        }
        showChildren = !showChildren;
    }
    
    public void addSubBlock(Constituent c, int colorIndex) {
           Block child = new Block(c,colorIndex+1);
           children.add(child);
           contentBox.add(child);
           contentBox.add(newSpacer());
    }
    
    public void addBlockListener(BlockListener listener) {
        blockListeners.add(listener);
        for (Block child : children) {
            child.addBlockListener(listener);
        }
    }
    
    private class ClickedBox implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                for (BlockListener listener : blockListeners) {
                    listener.selectedConstituent(constituent);
                }
            }
            if (e.getClickCount() == 2) {
                toggleChildren();
            }        
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}
    }
    
    private class TransferableBlock implements Transferable {
        private Block block;
        public TransferableBlock(Block block) {
            this.block = block;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] {blockFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(blockFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {

            if (flavor.equals(blockFlavor))
                return block;
            else
                throw new UnsupportedFlavorException(flavor);
        }
    }
    

    private class DragGestureListImp implements DragGestureListener {

        @Override
        public void dragGestureRecognized(DragGestureEvent event) {
            Cursor cursor = null;
            Block source = (Block) event.getComponent();

            if (event.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            event.startDrag(cursor, new TransferableBlock(source));
        }
    }
    
    private class SpacerDropListener extends DropTargetAdapter 
            implements DropTargetListener {
    
        private DropTarget dropTarget;
        private Box spacer;
        
        public SpacerDropListener(Box spacer) {
            this.spacer = spacer;
            dropTarget = new DropTarget(spacer, DnDConstants.ACTION_COPY, this,
                    true, null);
        }
        
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                Block source = (Block) tr.getTransferData(blockFlavor);
        
                if (!Block.this.equals(source) 
                        && event.isDataFlavorSupported(blockFlavor)) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    System.out.println("Drag success");
                    spacer.setBackground(null);

                }
                else {
                    event.rejectDrop();
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
        
        public void dragOver(DropTargetDragEvent event) {
            try {
                Transferable tr = event.getTransferable();
                Block source = (Block) tr.getTransferData(blockFlavor);
                if (Block.this.equals(source)) {
                    System.out.println("Hover self");
                }
                else if (event.isDataFlavorSupported(blockFlavor)) {
                    System.out.println("Hover other");
                    spacer.setBackground(Color.BLACK);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void dragExit(DropTargetEvent event) {
            spacer.setBackground(null);
            
        }
        
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Block))
            return false;
        
        Block otherBlock = (Block) other;
        return constituent.equals(otherBlock.constituent);
    }
}
