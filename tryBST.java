import java.util.*;
//Main Binary search tree class
public class tryBST {
    private tNode root;
    private int nodeCount;
    //----Constructor----
    public tryBST() {
        root = null;
        nodeCount = 0;
    }
    //Inserting the method
    public void insert(int key) {
        root = insertRec(root, key);
        nodeCount++;
    }
    private tNode insertRec(tNode node, int key) {
        if (node == null) {
            return new tNode(key);
        }
         if (key < node.key) {
            node.left = insertRec(node.left, key);
        } else if (key > node.key) {
            node.right = insertRec(node.right, key);
        }
        // If key equals node.key, do nothing (no duplicates)
        
        return node;
    }
    //Checking if tree satisfies the BST properties

}