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
            System.out.println("Duplicates not allowed.");
            return node;
        }

        if (data < node.data) {
            // If the data is less than the root's data, go to the left subtree
            node.left = insertRec(node.left, data, node);
        } else if (data > node.data) {
            // If the data is greater than the root's data, go to the right subtree
            node.right = insertRec(node.right, data, node); // node is now the parent of the newnode
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
            System.out.println("***");
            System.out.println("Performing right rotation.");
            System.out.println("***");
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
            System.out.println("***");
            System.out.println("Performing left rotation.");
            System.out.println("***");
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
            System.out.println("***");
            System.out.println("Performing left-right rotation.");
            System.out.println("***");
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
            System.out.println("***");
            System.out.println("Performing right-left rotation.");
            System.out.println("***");
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
            System.out.println("Data not found.");
            return node;
        }

        if (node == null) {
            return node;
        }

        if (node.data < data) {
            node.left = removeRec(node.left, data);
        } else if (node.data > data) {
            node.right = removeRec(node.right, data);
        } else {
            if (node.left == null) {
                transplant(node, node.right);
            } else if (node.right == null) {
                transplant(node, node.left);
            } else {
                Node minimumOfRight = minimum(node.right);
                if (minimumOfRight.parent != node) {
                    transplant(minimumOfRight, minimumOfRight.right);
                    minimumOfRight.right = node.right;
                    minimumOfRight.right.parent = minimumOfRight;
                }
                transplant(node, minimumOfRight);
                minimumOfRight.left = node.left;
                minimumOfRight.left.parent = minimumOfRight;
            }
        }

        Node x = node.parent; // Check up the tree
        while (x != null) {
        updateHeight(x);
        int balanceFactor = getBalanceFactor(x);

        // Balancing tree
        // Left Left Case
        if (balanceFactor > 1 && getBalanceFactor(x.left) >= 0) {
            System.out.println("***");
            System.out.println("Performing right rotation.");
            System.out.println("***");
            x = rightRotation(x);
        }

        // Right Right Case
        if (balanceFactor < -1 && getBalanceFactor(x.right) <= 0) {
            System.out.println("***");
            System.out.println("Performing left rotation.");
            System.out.println("***");
            x = leftRotation(x);
        }

        // Left Right Case
        if (balanceFactor > 1 && getBalanceFactor(x.left) < 0) {
            System.out.println("***");
            System.out.println("Performing left-right rotation.");
            System.out.println("***");
            x.left = leftRotation(x.left);
            x = rightRotation(x);
        }

        // Right Left Case
        if (balanceFactor < -1 && getBalanceFactor(x.right) > 0) {
            System.out.println("***");
            System.out.println("Performing right-left rotation.");
            System.out.println("***");
            x.right = rightRotation(x.right);
            x = leftRotation(x);
        }
            x = x.parent; // Going up
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

        updateHeight(node);
        updateHeight(leftOfNode);

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

        updateHeight(node);
        updateHeight(rightOfNode);

        return rightOfNode;
    }

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


    private void updateHeight(Node node) {
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
        int parentData = 0;
        if (node.parent != null) {
            parentData = node.parent.data;
        }
        return parentData;
    }

    public Node getParent(Node node) {
        return node != null ? node.parent : null;
    }

    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);             // Visit left subtree
            System.out.print(root.data + " "); // Visit root
            inorderRec(root.right);            // Visit right subtree
        }
    }

    public void preorder() {
        preorderRec(root);
    }

    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " "); // Visit root
            preorderRec(root.left);            // Visit left subtree
            preorderRec(root.right);           // Visit right subtree
        }
    }

    public void postorder() {
        postorderRec(root);
    }

    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);           // Visit left subtree
            postorderRec(root.right);          // Visit right subtree
            System.out.print(root.data + " "); // Visit root
        }
    }

    public void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(Node root, int level) {
        if (root != null) {
            // Print spaces proportional to the level
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }

            // Print root data, its height, and balance factor
            System.out.println(root.data + " Height: " + root.height + " Balance Factor: " + 
                                getBalanceFactor(root) + " Parent: " + getParentData(root));

            int nextLevel = level + 1;

            // Recursively print left and right subtrees, with increased level
            if (root.left != null) {
                printTreeRec(root.left, nextLevel);
            } else { // If node.left is  is null, print a _ in the place
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }

            if (root.right != null) {
                printTreeRec(root.right, nextLevel);
            } else { // If node.right is  is null, print a _ in the place
                for (int i = 0; i < nextLevel; i++) {
                    System.out.print("    ");
                }
                System.out.println("_");
            }
        }
    }

    private int getLevel(Node node) {
        return node.height - 1;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        // return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
        return getHeight(node.left) - getHeight(node.right);
    }

    public int getMaximumData() { // Based on root
        if (root == null) {
            return 0;
        }
        Node max = maximum(root);
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

    public int getMinimumData() { // Based on root
        if (root == null) {
            return 0;
        }
        Node min = minimum(root);
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

    public int getSuccessorData() { // Based on root
        if (root == null) {
            return 0;
        }
        Node successor = successor(root);
        if (successor == null) { // Case tree has no right nodes, it will return the parent in the
            return 0;            // called function, if the parent is null a null pointer excep
        } else {                 // will occur without this if
            return successor.data; 
        }
    }

/*
        15
       /  \
     6     18
    / \    / \
   3   7  17  20
  / \   \
 2  4   13
        /
       9

Start with x = 13.

13 has no right child.

Move up: y = 7 (parent of 13).

13 is the right child of 7.
Move up: y = 6 (parent of 7).

7 is the right child of 6.
Move up: y = 15 (parent of 6).

6 is the left child of 15.
Successor of 13: Node 15.

When Node x Does Not Have a Right Subtree:

Traverse the ancestors until you find one that is not a right child.
 Step-by-step for 13:
 13 → up to 7 → up to 6 → up to 15 (left child of 15)
 Successor of 13 is 15

Visual Example with Traversing Up:
Start with:
    13 (find parent)
-> 7 (13 == parent's right? yes, move up)
-> 6 (7 == parent's right? yes, move up)
-> 15 (6 == parent's left? stop!)
Successor is 15
Conclusion
Right Subtree Absent: Traverse upward using parent pointers until you find an ancestor that is not
the right child of its parent.
*/
    public Node successor(Node node) { // Based on root
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

    public int getPredecessorData() {
        if (root == null) {
            return 0;
        }
        Node predecessor = predecessor(root);
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

    public Node searchNodeRec(Node node, int data) {
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
}
