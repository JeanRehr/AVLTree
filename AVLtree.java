class Node {
    int data;
    int height;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
        this.height = 1;
    }
}

public class AVLtree {
    Node root;  // The root node of the tree

    // Public method to insert a new value in the tree
    public void insert(int data) {
        root = insertRec(root, data);
    }

    // Helper method to recursively insert a new value in the tree
    private Node insertRec(Node root, int data) {
        // If the tree/subtree is empty, create a new node
        if (root == null) {
            root = new Node(data);
        }

        if (data < root.data && data != root.data) {
            // If the data is less than the root's data, go to the left subtree
            root.left = insertRec(root.left, data);
        } else if (data > root.data && data != root.data) {
            // If the data is greater than the root's data, go to the right subtree
            root.right = insertRec(root.right, data);
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));


        // Balancing tree
        /*
Rotations Required for Balancing:
To maintain the AVL property, you need to implement the following rotations:

Right Rotation (RR):

Performed when a left-heavy situation occurs.
Left Rotation (LL):

Performed when a right-heavy situation occurs.
Left-Right Rotation (LR):

Performed when a left-right-heavy situation occurs.
Right-Left Rotation (RL):

Performed when a right-left-heavy situation occurs.
Steps for Correct AVL Tree Insertion:
Insert Node:

Follow the standard Binary Search Tree insertion logic.
Update Heights:

After insertion, update the height of each node back up to the root.
Compute Balance Factor:

Compute balance factors for nodes during the backtracking phase of the recursion.
Rebalance if Necessary:

Perform appropriate rotations based on the computed balance factors.
Integration of Rotations:
Here’s a conceptual outline of how you can rebalance the tree after insertion based on the balance factors:

Right Rotation (RR):

If balance > 1 and new node < left child.
Left Rotation (LL):

If balance < -1 and new node > right child.
Left-Right Rotation (LR):

If balance > 1 and new node > left child.
Right-Left Rotation (RL):

If balance < -1 and new node < right child.
Example Outline:
Right Rotate if balance factor > 1 and data less than left child’s data.
Left Rotate if balance factor < -1 and data greater than right child's data.
Left-Right Rotate if balance factor > 1 and data greater than left child’s data.
Right-Left Rotate if balance factor < -1 and data less than right child’s data.
Summary of Rotation Functions:
Right Rotate:

Update pointers and change heights of affected nodes correctly.
Left Rotate:

Update pointers and change heights of affected nodes correctly.
Left-Right Rotate (Combination):

First, perform a left rotation on the child node, then a right rotation on the current node.
Right-Left Rotate (Combination):

First, perform a right rotation on the child node, then a left rotation on the current node.
        */

        if (getBalanceFactor(root) > 1) {

        } else if (getBalanceFactor(root) < -1) {

        }

        return root;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    // Method to perform an in-order traversal: visit left child, root, right child
    public void inorder() {
        inorderRec(root);
    }

    // Helper method to recursively perform an in-order traversal
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);             // Visit left subtree
            System.out.print(root.data + " "); // Visit root
            inorderRec(root.right);            // Visit right subtree
        }
    }

    // Method to perform a pre-order traversal: visit root, left child, right child
    public void preorder() {
        preorderRec(root);
    }

    // Helper method to recursively perform a pre-order traversal
    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " "); // Visit root
            preorderRec(root.left);            // Visit left subtree
            preorderRec(root.right);           // Visit right subtree
        }
    }

    // Method to perform a post-order traversal: visit left child, right child, root
    public void postorder() {
        postorderRec(root);
    }

    // Helper method to recursively perform a post-order traversal
    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);           // Visit left subtree
            postorderRec(root.right);          // Visit right subtree
            System.out.print(root.data + " "); // Visit root
        }
    }

    // Method to print tree structure visually
    public void printTree() {
        printTreeRec(root, 0);
    }

    // Helper method to recursively print the tree structure
    private void printTreeRec(Node root, int level) {
        if (root != null) {
            // Print spaces proportional to the level to represent tree depth visually
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }

            // Print root data, its height, and balance factor
            System.out.println(root.data + " (Height: " + root.height + ") " + "(balance factor: " + getBalanceFactor(root) + ")");
            // Recursively print left and right subtrees, with increased level
            printTreeRec(root.left, level + 1);
            printTreeRec(root.right, level + 1);
        }
    }

    // Method to get the depth (maximum height) of the tree
    public int getDepth() {
        return getDepthRec(root);
    }

    // Helper method to recursively compute the depth of the tree
    private int getDepthRec(Node node) {
        if (node == null) {
            return 0; // Base case: depth of an empty tree is 0
        }
        // Calculate the depth of each subtree
        int leftDepth = getDepthRec(node.left);
        int rightDepth = getDepthRec(node.right);

        // Return the larger depth plus one for the current node
        return Math.max(leftDepth, rightDepth) + 1;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return leftHeight - rightHeight;
    }
}
