class Node {
    int data;
    int height;
    Node left;
    Node right;
    Node parent;

    public Node(int data) {
        this.data = data;
        this.height = 1;
    }
}

public class AVLtree {
    Node root;  // The root node of the tree

    public void insert(int data) {
        root = insertRec(root, data, null);
    }

    private Node insertRec(Node node, int data, Node parent) {
        // If the tree/subtree is empty, create a new node
        if (node == null) {
            Node newNode = new Node(data);
            newNode.parent = parent;
            return newNode;
        }

        if (search(data)) {
            System.out.println("*** Duplicates not allowed: " + data + ". ***");
            return node;
        }

        if (data < node.data) {
            // If the data is less than the node's data, go to the left subtree
            node.left = insertRec(node.left, data, node); // node is now the parent of the newnode
        } else if (data > node.data) {                    // as we go down the tree
            // If the data is greater than the node's data, go to the right subtree
            node.right = insertRec(node.right, data, node);
        }

        setHeight(node);
        int balanceFactor = getBalanceFactor(node);

        // Balancing tree
        // Left Left Case
        if (balanceFactor > 1 && data < node.left.data) {
            System.out.println("*** Performing right rotation on " + node.data + ". ***");
            return rightRotation(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && data > node.right.data) {
            System.out.println("*** Performing left rotation on " + node.data + ". ***");
            return leftRotation(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && data > node.left.data) {
            System.out.println(
                "*** Performing left-right rotation on " + node.left.data +
                " and " + node.data + ". ***"
            );
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && data < node.right.data) {
            System.out.println(
                "*** Performing right-left rotation on " + node.right.data +
                " and " + node.data + ". ***"
            );
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;
    }

    public void remove(int data) {
        root = removeRec(root, data);
    }

    // Copy
    private Node removeRec(Node node, int data) {
        if (!search(data)) {
            System.out.println("*** Data not found. ***");
            return node;
        }

        if (data < node.data) { // Traverse left in the tree
            node.left = removeRec(node.left, data);
        } else if (data > node.data) { // Traverse right in the tree
            node.right = removeRec(node.right, data);
        } else { // Node found
            if (node.left == null || node.right == null) { // Node with only one or no child
                Node temp = (node.left != null) ? node.left : node.right;

                if (temp == null) { // No child case
                    temp = node;
                    node = null;
                } else { // One child case
                    temp.parent = node.parent; // Update parent 
                    node = temp; // Node to be "deleted" will be equal to either right or left
                }

            } else { // Two children deletion
                Node temp = minimum(node.right); // The successor on the right of the deleted node
                node.data = temp.data; // Assign the data in the temp to the node to be "deleted"
                node.right = removeRec(node.right, temp.data); // remove the right of the node
            }                                                  // as it was assigned to node already
        }

        if (node == null) {
            return node;
        }

        setHeight(node);
        int balanceFactor = getBalanceFactor(node);

        // Balancing tree
        // Left Left Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            System.out.println("*** Performing right rotation on " + node.data + ". ***");
            return rightRotation(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            System.out.println("*** Performing left rotation on " + node.data + ". ***");
            return leftRotation(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            System.out.println(
                "*** Performing left-right rotation on " + node.left.data +
                " and " + node.data + ". ***"
            );
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            System.out.println(
                "*** Performing right-left rotation on " + node.right.data +
                " and " + node.data + ". ***"
            );
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;
    }
    
    // Remove an entire sub-tree
    public void massRemove(int data) {
        root = massRemoveRec(root, data);
    }
    public Node massRemoveRec(Node node, int data) {
        if (!search(data)) {
            System.out.println("*** Data not found. ***");
            return node;
        }

        if (data < node.data) {
            node.left = massRemoveRec(node.left, data);
        } else if (data > node.data) {
            node.right = massRemoveRec(node.right, data);
        } else { // Node found
                node = null; // Remove as if they are leaf
        }
        
        if (node == null) {
            return node;
        }

        setHeight(node);
        int balanceFactor = getBalanceFactor(node);

        // Balancing tree
        // Left Left Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            System.out.println("*** Performing right rotation on " + node.data + ". ***");
            return rightRotation(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            System.out.println("*** Performing left rotation on " + node.data + ". ***");
            return leftRotation(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            System.out.println(
                "*** Performing left-right rotation on " + node.left.data +
                " and " + node.data + ". ***"
            );
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            System.out.println(
                "*** Performing right-left rotation on " + node.right.data +
                " and " + node.data + ". ***"
            );
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;
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
    private Node rightRotation(Node node) { // 4 where imbalance happens
        Node leftOfNode = node.left; // 2 the new root of subtree
        Node rightOfLeftNode = leftOfNode.right; // 3 right of the left of node

        leftOfNode.right = node; // 4 goes to the left of 2
        node.left = rightOfLeftNode; // 3 goes to the left of 4

        // Update parents
        if (rightOfLeftNode != null) {
            rightOfLeftNode.parent = node; // 3 is now parent of 4
        }
        leftOfNode.parent = node.parent; // the parent of 4 is now the parent of 2
        node.parent = leftOfNode; // parent of 4 turns to 2

        setHeight(node);
        setHeight(leftOfNode);

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
    private Node leftRotation(Node node) {
        Node rightOfNode = node.right;
        Node leftOfRightNode = rightOfNode.left;

        rightOfNode.left = node;
        node.right = leftOfRightNode;

        // Update parents
        if (leftOfRightNode != null) {
            leftOfRightNode.parent = node;
        }
        rightOfNode.parent = node.parent;
        node.parent = rightOfNode;

        setHeight(node);
        setHeight(rightOfNode);

        return rightOfNode;
    }

    // not used    
    private void transplant(Node toBeTransplanted, Node newRoot) {
        if (toBeTransplanted.parent == null) {
            this.root = newRoot;
        } else if (toBeTransplanted == toBeTransplanted.parent.left) {
            toBeTransplanted.parent.left = newRoot;
        } else {
            toBeTransplanted.parent.right = newRoot;
        }

        if (newRoot != null) {
            newRoot.parent = toBeTransplanted.parent;
        }
    }


    private void setHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    public int getRootData() {
        if (this.root == null) {
            return 0;
        }
        return this.root.data;
    }

    public Node getRoot() {
        return this.root;
    }

    public int getParentData(Node node) {
        return node.parent != null ? node.parent.data : 0;
    }

    public Node getParent(Node node) {
        return node != null ? node.parent : null;
    }

    public void printTree(Node node) {
        printTreeRec(node, 0);
    }

    private void printTreeRec(Node node, int level) {
        if (node != null) {
            // Print spaces proportional to the level
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }

            // Print node data, its height, balance factor, and parent
            System.out.println(
                node.data + " Height: " + node.height +
                " Balance Factor: " + getBalanceFactor(node) +
                " Parent: " + ((node.parent == null) ? "Null" : getParentData(node))
            );

            int nextLevel = level + 1;

            // Recursively print left and right subtrees, with increased level
            if (node.left != null) {
                printTreeRec(node.left, nextLevel);
            } else { // If node.left is  is null, print a _ in the place
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }

            if (node.right != null) {
                printTreeRec(node.right, nextLevel);
            } else { // If node.right is  is null, print a _ in the place
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }
        } else {
            System.out.println("Tree is empty.");
        }
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        // return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
        return getHeight(node.left) - getHeight(node.right);
    }

    public int getMaximumData(Node node) {
        if (node == null) {
            return 0;
        }
        Node max = maximum(node);
        return max.data;
    }

    public Node maximum(Node node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public int getMinimumData(Node node) {
        if (node == null) {
            return 0;
        }
        Node min = minimum(node);
        return min.data;
    }

    public Node minimum(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Just testing
    public Node minimumRec(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        } else {
            return minimumRec(node.left);
        }
    }

    public int getSuccessorData(Node node) {
        if (node == null) {
            return 0;
        }
        Node successor = successor(node);
        if (successor == null) { // Case tree has no right nodes, it will return the parent in the
            return 0;            // called function, if the parent is null a null pointer excep
        } else {                 // will occur without this if
            return successor.data;
        }
    }

    public Node successor(Node node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return minimum(node.right);
        }

        Node y = node.parent;
        while (y != null && node == y.right) {
            node = y;
            y = y.parent;
        }
        return y;
    }

    public int getPredecessorData(Node node) {
        if (node == null) {
            return 0;
        }
        Node predecessor = predecessor(node);
        if (predecessor == null) { // Case tree has no left nodes, it will return the parent in the
            return 0;              // called function, if the parent is null a null pointer excep
        } else {                   // will occur without this if
            return predecessor.data; 
        }
    }

    public Node predecessor(Node node) {
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            return maximum(node.left);
        }

        Node y = node.parent;
        while (y != null && node == y.left) {
            node = y;
            y = y.parent;
        }
        return y;
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

    // Search algo returning the node to use in other functions in middle of tree, like find the 
    // minimum of a sub-tree.
    public Node searchNode(int data) {
        return searchNodeRec(root, data);
    }

    private Node searchNodeRec(Node node, int data) {
        if (node == null) {
            return null;
        }

        if (node.data == data) {
            return node;
        }

        if (data < node.data) {
            return searchNodeRec(node.left, data);
        } else {
            return searchNodeRec(node.right, data);
        }
    }

    public void preorder() {
        preorderRec(root);
        System.out.println("");
    }

    private void preorderRec(Node node) {
        if (node != null) {
            System.out.print(node.data + " "); // Visit node
            preorderRec(node.left);            // Visit left subtree
            preorderRec(node.right);           // Visit right subtree
        }
    }

    public void postorder() {
        postorderRec(root);
        System.out.println("");
    }

    private void postorderRec(Node node) {
        if (node != null) {
            postorderRec(node.left);           // Visit left subtree
            postorderRec(node.right);          // Visit right subtree
            System.out.print(node.data + " "); // Visit node
        }
    }

    public void inorder() {
        inorderRec(root);
        System.out.println("");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);             // Visit left subtree
            System.out.print(node.data + " "); // Visit node
            inorderRec(node.right);            // Visit right subtree
        }
    }
}
