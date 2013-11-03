package semantics.view;

import grammar.model.Category;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import semantics.model.Constituent;

@SuppressWarnings("serial")
public class DraggableButton extends JButton {
    private Constituent category;
    private static DataFlavor buttonFlavor;    
    private ArrayList<DraggableButtonListener> listeners;
    
    protected static DataFlavor getFlavor() {
        return buttonFlavor;
    }

    protected DraggableButton(Constituent constituent) {
        super(constituent.getLabel());
        this.category = constituent;
        listeners = new ArrayList<>();
        try { 
            buttonFlavor = new DataFlavor(
                    DataFlavor.javaJVMLocalObjectMimeType + 
                    ";class=\"" + DraggableButton.class.getName() + "\"");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        addMouseListener(new ClickedBox());
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(
                this, DnDConstants.ACTION_COPY, new DragListener());
        
    }

    private class ClickedBox implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (DraggableButtonListener listener : listeners) {
                listener.buttonPressed(category);
            }
        }

        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    
    protected Constituent getConstituent() {
        return Constituent.copy(category);
    }
    
    private class TransferableButton implements Transferable {
        private DraggableButton button;
        public TransferableButton(DraggableButton button) {
            this.button = button;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] {buttonFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(buttonFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {

            if (flavor.equals(buttonFlavor)) {
                return button;
            }
            else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
    
    private class DragListener implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent event) {
            Cursor cursor = null;
            DraggableButton source = (DraggableButton) event.getComponent();
            if (event.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            event.startDrag(cursor, new TransferableButton(source));
        }
    }
}
