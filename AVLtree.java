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

    public void insert(int data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, int data) {
        // If the tree/subtree is empty, create a new node
        if (node == null) {
            node = new Node(data);
        }

        if (search(data)) {
            System.out.println("Duplicates not allowed.");
            return node;
        }

        if (data < node.data) {
            // If the data is less than the root's data, go to the left subtree
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            // If the data is greater than the root's data, go to the right subtree
            node.right = insertRec(node.right, data);
        }

        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        // Balancing tree
        // Left Left Case
        if (balanceFactor > 1 && data < node.left.data) {
            System.out.println("***");
            System.out.println("Performing right rotation.");
            System.out.println("***");
            return rightRotation(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && data > node.right.data) {
            System.out.println("***");
            System.out.println("Performing left rotation.");
            System.out.println("***");
            return leftRotation(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && data > node.left.data) {
            System.out.println("***");
            System.out.println("Performing left-right rotation.");
            System.out.println("***");
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right Left Case
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
        removeRec(root, data);
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

        if (data < node.data) {
            node.left = removeRec(node.left, data);
        } else if (data > node.data) {
            node.right = removeRec(node.right, data);
        } else {

        }

        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

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
    private Node rightRotation(Node node) {
        Node leftOfNode = node.left;
        Node rightOfLeftNode = leftOfNode.right;

        leftOfNode.right = node;
        node.left = rightOfLeftNode;


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

        updateHeight(node);
        updateHeight(rightOfNode);

        return rightOfNode;
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
            // Print spaces proportional to the level to represent tree depth visually
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }

            // Print root data, its height, and balance factor
            System.out.println(root.data + " (Height: " + root.height + ") " +
                            "(Balance Factor: " + getBalanceFactor(root) + ")");

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
