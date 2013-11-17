package rule.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;

import commons.main.MainFrame;

/*
 * http://www.javaprogrammingforums.com/java-swing-tutorials
 *      /7944-how-use-jtree-create-file-system-viewer-tree.html
 */
public class NewRuleTree extends JPanel {
    private JTree fileTree;
    private FileSystemModel fileSystemModel;
    private JTextArea fileDetailsTextArea = new JTextArea();
    private File selected;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        NewRuleTree panel = new NewRuleTree();
        frame.setPanel(panel);
    }

    public NewRuleTree() {
        String directory = "rule";
        fileDetailsTextArea.setEditable(false);
        fileSystemModel = new FileSystemModel(new File(directory));
        fileTree = new JTree(fileSystemModel);
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent event) {
              File file = (File) fileTree.getLastSelectedPathComponent();
              fileDetailsTextArea.setText(getFileDetails(file));
            }
        });
        
        fileTree.addMouseListener(new TreeListener());
        fileTree.setEditable(true);
        setLayout(new MigLayout());
        add(fileTree);
    }

    private String getFileDetails(File file) {
        if (file == null)
            return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + file.getName() + "\n");
        buffer.append("Path: " + file.getPath() + "\n");
        buffer.append("Size: " + file.length() + "\n");
        return buffer.toString();
    }
    
    private class TreeListener extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) {
            int row = fileTree.getRowForLocation(e.getX(), e.getY());
            TreePath path = fileTree.getPathForLocation(e.getX(), e.getY());
            if (row != -1 && SwingUtilities.isRightMouseButton(e)) {
                String filename = StringUtils.join(path.getPath(), "/");
                File file = new File(filename);
                selected = file;
                rightClicked(file, e.getX(), e.getY());
            }
        }
    }
    
    
    private void rightClicked(File file, int x, int y) {
    
        // check if folder or file
            // if if(file.isDirectory()){, folder na kagad else file na un tapos may lalabas na context menu
        //if folder, add file/folder, edit/delete folder
        //if file, edit and delete
        
        if (file.isDirectory()) {
            JPopupMenu popup = folderPopup();
            popup.show(this, x, y);
        }
    }
    
    private JPopupMenu folderPopup() {
        JPopupMenu result = new JPopupMenu();
        
        // New project menu item
        JMenuItem menuItem = new JMenuItem("Add Folder",new ImageIcon("images/newproject.png"));
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.getAccessibleContext().setAccessibleDescription("Add Folder");
        menuItem.addActionListener(new AddFolderListener());
        result.add(menuItem);
        
        // New File menu item
        menuItem = new JMenuItem("Edit Folder", new ImageIcon("images/newfile.png"));
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.addActionListener(new EditFolderListener());
        result.add(menuItem);
        
        menuItem = new JMenuItem("Delete Folder", new ImageIcon("images/newfile.png"));
        menuItem.setMnemonic(KeyEvent.VK_F);
        menuItem.addActionListener(new DeleteFolderListener());
        result.add(menuItem);
        
        return result;
    }
    

    /* 
     * Private Listeners
     */
    private class AddFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Adding: " + selected);
        }    
    }
    
    private class EditFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Editing: " + selected);
        }    
    }
    
    private class DeleteFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Deleting: " + selected);
        }    
    }
    
}

class FileSystemModel implements TreeModel {
    private File root;

    private Vector listeners = new Vector();

    public FileSystemModel(File rootDirectory) {
        root = rootDirectory;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        String[] children = directory.list();
        return new TreeFile(directory, children[index]);
    }

    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (fileList != null)
                return file.list().length;
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        String[] children = directory.list();
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;

    }

    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
        Object[] changedChildren = { targetFile };
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }

    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }

    private class TreeFile extends File {
        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        public String toString() {
            return getName();
        }
    }
}

