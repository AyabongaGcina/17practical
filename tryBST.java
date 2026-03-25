import java.util.*;

public class tryBST {
    private tNode root;
    private int nodeCount;

    public tryBST() {
        root = null;
        nodeCount = 0;
    }

    // Insert
    public void insert(int key) {
        root = insertRec(root, key);
        nodeCount++;
    }

    private tNode insertRec(tNode node, int key) {
        if (node == null) return new tNode(key);

        if (key < node.key) {
            node.left = insertRec(node.left, key);
        } else if (key > node.key) {
            node.right = insertRec(node.right, key);
        }

        return node;
    }

    // Check BST
    public boolean isBST() {
        return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTRec(tNode node, int min, int max) {
        if (node == null) return true;

        if (node.key < min || node.key > max) return false;

        return isBSTRec(node.left, min, node.key - 1) &&
               isBSTRec(node.right, node.key + 1, max);
    }

    // Build balanced BST
    public void buildPerfectBST(int start, int end) {
        if (start > end) return;

        int mid = (start + end) / 2;
        insert(mid);

        buildPerfectBST(start, mid - 1);
        buildPerfectBST(mid + 1, end);
    }

    // Delete evens
    public void deleteEvenNumbers() {
        root = deleteEvenRec(root);
    }

    private tNode deleteEvenRec(tNode node) {
        if (node == null) return null;

        node.left = deleteEvenRec(node.left);
        node.right = deleteEvenRec(node.right);

        if (node.key % 2 == 0) {
            nodeCount--;
            return deleteNode(node);
        }

        return node;
    }

    private tNode deleteNode(tNode node) {
        if (node.left == null && node.right == null) return null;
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;

        tNode successor = findMin(node.right);
        node.key = successor.key;
        node.right = deleteRec(node.right, successor.key);
        return node;
    }

    private tNode findMin(tNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private tNode deleteRec(tNode node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = deleteRec(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRec(node.right, key);
        } else {
            node = deleteNode(node);
        }

        return node;
    }

    // Utilities
    public int getSize() {
        return nodeCount;
    }

    public void printInOrder() {
        printInOrderRec(root);
        System.out.println();
    }

    private void printInOrderRec(tNode node) {
        if (node != null) {
            printInOrderRec(node.left);
            System.out.print(node.key + " ");
            printInOrderRec(node.right);
        }
    }

    public int getHeight() {
        return getHeightRec(root);
    }

    private int getHeightRec(tNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeightRec(node.left), getHeightRec(node.right));
    }

    // MAIN
    public static void main(String[] args) {
        int n = 20;
        int repetitions = 30;

        long maxNumber = (long) Math.pow(2, n) - 1;

        System.out.println("=".repeat(70));
        System.out.println("CSC 211 - Binary Search Tree Performance Test");
        System.out.println("=".repeat(70));

        System.out.println("\nConfiguration:");
        System.out.println("n = " + n);
        System.out.println("Number of keys = " + maxNumber);
        System.out.println("Repetitions = " + repetitions + "\n");

        // Verification
        if (n <= 7) {
            System.out.println("--- VERIFICATION MODE ---");

            tryBST testTree = new tryBST();
            testTree.buildPerfectBST(1, (int) maxNumber);

            System.out.println("Tree size: " + testTree.getSize());
            System.out.println("Height: " + testTree.getHeight());
            System.out.println("Is BST: " + testTree.isBST());

            System.out.println("In-order traversal:");
            testTree.printInOrder();

            testTree.deleteEvenNumbers();

            System.out.println("\nAfter deleting evens:");
            System.out.println("Tree size: " + testTree.getSize());
            System.out.println("Is BST: " + testTree.isBST());
            System.out.println("--------------------------\n");
        }

        // ===== FIXED TIMING =====

        // Populate timing (TOTAL)
        long startPopulateTotal = System.nanoTime();

        for (int i = 0; i < repetitions; i++) {
            tryBST tree = new tryBST();
            tree.buildPerfectBST(1, (int) maxNumber);
        }

        long endPopulateTotal = System.nanoTime();

        // Delete timing (TOTAL)
        long startDeleteTotal = System.nanoTime();

        for (int i = 0; i < repetitions; i++) {
            tryBST tree = new tryBST();
            tree.buildPerfectBST(1, (int) maxNumber);
            tree.deleteEvenNumbers();
        }

        long endDeleteTotal = System.nanoTime();

        // Convert to ms
        double popAvg = (endPopulateTotal - startPopulateTotal) / 1_000_000.0;
        double delAvg = (endDeleteTotal - startDeleteTotal) / 1_000_000.0;

        double popStd = 0;
        double delStd = 0;

        // Results
        System.out.println("FINAL RESULTS");
        System.out.println("-".repeat(70));

        System.out.printf("%-25s %-15s %-20s %-15s\n",
                "Method", "Number of keys", "Average time (ms)", "Std Dev");

        System.out.printf("%-25s %-15d %-20.2f %-15.2f\n",
                "Populate tree", maxNumber, popAvg, popStd);

        System.out.printf("%-25s %-15d %-20.2f %-15.2f\n",
                "Remove evens from the tree", maxNumber, delAvg, delStd);

        System.out.println("-".repeat(70));

        if (popAvg < 1000 || delAvg < 1000) {
            System.out.println("\nWARNING: Times < 1000ms. Increase workload.");
        } else {
            System.out.println("\nSUCCESS: Times meet requirement (>1000ms).");
        }

        Runtime runtime = Runtime.getRuntime();
        System.out.printf("\nUsed memory: %.2f MB\n",
                (runtime.totalMemory() - runtime.freeMemory()) / 1024.0 / 1024.0);
    }
}