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
     public void buildPerfectBST(int start, int end) {
        if (start > end) {
            return;
        }
         int middle = (start + end) / 2;
        insert(middle);
        
        buildPerfectBST(start, middle - 1);
        buildPerfectBST(middle + 1, end);
    }
    //DELETE ALL NODES WITH EVEN NUMBERS
    public void deleteEvenNumbers() {
        root = deleteEvenRec(root);
    }
    
    private tNode deleteEvenRec(tNode node) {
        if (node == null) {
            return null;
        }
        
        // First, recursively process left and right subtrees
        node.left = deleteEvenRec(node.left);
        node.right = deleteEvenRec(node.right);
        
        // If current node's key is even, delete it
        if (node.key % 2 == 0) {
            nodeCount--;
            return deleteNode(node);
        }
        
        return node;
    }
    //Delete specific node and return the replacement
     private tNode deleteNode(tNode node) {
        // Case 1: Leaf node
        if (node.left == null && node.right == null) {
            return null;
        }
        
        // Case 2:One child
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        
        // Case 3:Two children - find inorder successor (smallest in right subtree)
        tNode successor = findMin(node.right);
        node.key = successor.key;
        node.right = deleteRec(node.right, successor.key);
        return node;
    }
    //FIND NODE WITH MINIMUM KEY IN A SUBTREE
    private tNode findMin(tNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    //DELETING SPECIFIC KEY FROM THE TREE
    private tNode deleteRec(tNode node, int key) {
        if (node == null) {
            return null;
        }
        
        if (key < node.key) {
            node.left = deleteRec(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRec(node.right, key);
        } else {
            node = deleteNode(node);
        }
        
        return node;
    }
    //UTILITY METHODS
}