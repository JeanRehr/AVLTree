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
    private Node insertRec(Node node, int data) {
        // If the tree/subtree is empty, create a new node
        if (node == null) {
            node = new Node(data);
        }

        if (data < node.data) {
            // If the data is less than the root's data, go to the left subtree
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            // If the data is greater than the root's data, go to the right subtree
            node.right = insertRec(node.right, data);
        } else {
            return node;
        }

        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        
        // Balancing tree
        // Left Left Case
        /*
        Condition: The balance factor is greater than 1, indicating a left-heavy tree.
        Additionally, the newly inserted data is less than the data in the left child of the node,
        meaning it has been inserted into the left subtree of the left child.
        Imbalance Type: Left-Left
        Rotation: Right Rotation
        Concept: The imbalance is corrected by performing a right rotation at the node. This
        involves moving the left child up to replace the node, making the node the right child of
        its previous left child. This rebalances the subtree by shifting the height distribution.
        
        */
        if (balanceFactor > 1 && data < node.left.data) {
            System.out.println("--------------------------------");
            System.out.println("Performing right rotation.");
            System.out.println("--------------------------------");
            return rightRotation(node);
        }

        // Right Right Case
        /*
        Condition: The balance factor is less than -1, indicating a right-heavy tree.
        Additionally, the newly inserted data is greater than the data in the right child of the
        node, meaning it has been inserted into the right subtree of the right child.
        Imbalance Type: Right-Right
        Rotation: Left Rotation
        Concept: The imbalance is corrected by performing a left rotation at the node. This
        involves moving the right child up to replace the node, making the node the left child of
        its previous right child. This rebalances the subtree by shifting the height distribution.
        */
        if (balanceFactor < -1 && data > node.right.data) {
            System.out.println("--------------------------------");
            System.out.println("Performing left rotation.");
            System.out.println("--------------------------------");
            return leftRotation(node);
        }

        // Left Right Case
        /*
        Condition: The balance factor is greater than 1, indicating a left-heavy tree.
        Additionally, the newly inserted data is greater than the data in the left child of the
        node, meaning it has been inserted into the right subtree of the left child.
        Imbalance Type: Left-Right
        Rotation: Left Rotation followed by Right Rotation
        Concept: This imbalance is corrected by performing two rotations:
        Step 1: Perform a left rotation on the left child, transforming the left-right structure
        into a left-left structure.
        Step 2: Perform a right rotation on the node itself to balance the newly formed left-left
        structure.
        */
        if (balanceFactor > 1 && data > node.left.data) {
            System.out.println("--------------------------------");
            System.out.println("Performing left-right rotation.");
            System.out.println("--------------------------------");
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right Left Case
        /*
        Condition: The balance factor is less than -1, indicating a right-heavy tree. Additionally,
        the newly inserted data is less than the data in the right child of the node, meaning it
        has been inserted into the left subtree of the right child.
        Imbalance Type: Right-Left
        Rotation: Right Rotation followed by Left Rotation
        Concept: This imbalance is corrected by performing two rotations:

        Step 1: Perform a right rotation on the right child, transforming the right-left structure
        into a right-right structure.
        Step 2: Perform a left rotation on the node itself to balance the newly formed right-right
        structure.
        */
        if (balanceFactor < -1 && data < node.right.data) {
            System.out.println("--------------------------------");
            System.out.println("Performing right-left rotation.");
            System.out.println("--------------------------------");
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;
    }

    public void remove(int data) {

    }

    // Copy
    private void removeRec(int data) {
    }

    /*
                4                          2
               / \                        / \
              2   6                      1   4
             / \               =>       /   / \
            1   3                     -1   3   6
           /
         -1
         4 = node               | 2 = leftOfRoot
         6 = node.right         | 4 = leftOfRoot.right (node)
         2 = node.left          | 1 = leftOfRoot.left
         1 = leftOfNode.right   | 3 = node.left
         3 = leftOfNode.left    | 6 = node.right
    */
    public Node rightRotation(Node node) {
        Node leftOfNode = node.left;
        Node rightOfLeftNode = leftOfNode.right;

        leftOfNode.right = node;
        node.left = rightOfLeftNode;


        updateHeight(leftOfNode);
        updateHeight(node);

        return leftOfNode;
    }

    /*
                4                          6
               / \                        / \
              2   6                      4   8
                 / \           =>       / \   \
                5   8                  2   5   9
                     \
                      9
         4 = node               | 6 = rightOfNode
         6 = node.right         | 4 = leftOfNode.right (node)
         2 = node.left          | 8 = leftOfNode.left
         5 = rightOfNode.left   | 2 = node.left
         8 = rightOfNode.right  | 5 = node.right
    */
    public Node leftRotation(Node node) {
        Node rightOfNode = node.right;
        Node leftOfRightNode = rightOfNode.left;

        rightOfNode.left = node;
        node.right = leftOfRightNode;

        updateHeight(rightOfNode);
        updateHeight(node);

        return rightOfNode;
    }


    private void updateHeight(Node node) {
        int heightLeft = 0;
        int heightRight = 0;

        heightLeft = getHeight(node.left);
        heightRight = getHeight(node.right);

        node.height = 1 + Math.max(heightLeft, heightRight);
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
                System.out.print("    ");
            }

            // Print root data, its height, and balance factor
            System.out.println(root.data + " (Height: " + root.height + ") " + "(Balance Factor: " + getBalanceFactor(root) + ")");

            int nextLevel = level + 1;

            // Recursively print left and right subtrees, with increased level
            if (root.left != null) {
                printTreeRec(root.left, nextLevel);
            } else {
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }

            if (root.right != null) {
                printTreeRec(root.right, nextLevel);
            } else {
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }
        }
    }

    public int getLevel(Node node) {
        return node.height - 1;
    }

    // Return Balance Factor of a given node
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return leftHeight - rightHeight;
    }

    public boolean search(int data) {
        return searchRec(root, data);
    }


    private boolean searchRec(Node node, int data) {
        if (node == null) {
            return false;
        }

        if (node.data == data) {
            return true;
        }

        /*
        if (searchRec(node.left, data) == false) {
            return searchRec(node.right, data);
        } else {
            return true;
        }
        */
        return searchRec(node.left, data) || searchRec(node.right, data);
    }
}
