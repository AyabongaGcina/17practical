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
     public boolean isBST() {
        return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isBSTRec(tNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        
        if (node.key < min || node.key > max) {
            return false;
        }
        
        return isBSTRec(node.left, min, node.key - 1) &&
               isBSTRec(node.right, node.key + 1, max);
    }
    //Creating tree in first-breadth order
}