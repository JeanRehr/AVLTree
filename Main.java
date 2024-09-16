public class Main {
    public static void main(String[] args) {
        AVLtree tree = new AVLtree();
        Text text = new Text();
        int j = 10;

        int userInt;
        short userOpt = 100;



        while (userOpt != 9) {
			text.options();

            userOpt = text.getUserOption((short) 12, (short) 0);

            switch (userOpt) {
            case 1: // Print Tree
                tree.printTree();
                break;
            case 2: // Search
                System.out.print("Search which number? ");
                userInt = text.getInt();
                System.out.println((tree.search(userInt)) ? "Data found." : "Not found.");
                break;
            case 3: // Insert
                System.out.print("Insert which number? ");
			    userInt = text.getInt();
                System.out.println("Tree before operation:");
                tree.printTree();
                tree.insert(userInt);
                System.out.println("Tree after operation:");
                tree.printTree();
                break;
            case 4: // Remove
                System.out.print("Remove which number? ");
			    userInt = text.getInt();
                System.out.println("Tree before operation:");
                tree.printTree();
                tree.remove(userInt);
                System.out.println("Tree after operation:");
                tree.printTree();
                break;
            case 5: // Preorder
                tree.preorder();
                break;
            case 6: // Postorder
                tree.postorder();
                break;
            case 7: // Inorder
                tree.inorder();
                break;
            case 8: // Help
                text.help();
                break;
            case 9: // Exit
                break;
            case 0: // Clear Console
                text.clearConsole();
                break;
            case 10:
                System.out.println(
                    "Minimum: " + tree.getMinimumData(tree.getRoot()) +
                    "\nMaximum: " + tree.getMaximumData(tree.getRoot()) +
                    "\nRoot of the tree: " + tree.getRootData() +
                    "\nSuccessor: " + tree.getSuccessorData(tree.getRoot()) +
                    "\nPredecessor: " + tree.getPredecessorData(tree.getRoot())
                );
                break;
            case 11:
                System.out.print("Operations on which node? ");
			    userInt = text.getInt();
                if (tree.searchNode(userInt) != null) {
                    System.out.println(
                        "Minimum of sub-tree: " +
                        tree.getMinimumData(tree.searchNode(userInt)) +
                        "\nMaximum of sub-tree: " +
                        tree.getMaximumData(tree.searchNode(userInt)) +
                        "\nRoot of the sub-tree: " +
                        userInt +
                        "\nSuccessor of sub-tree: " +
                        tree.getSuccessorData(tree.searchNode(userInt)) +
                        "\nPredecessor of sub-tree: " +
                        tree.getPredecessorData(tree.searchNode(userInt))
                    );
                } else { 
                    System.out.println("Data/sub-tree not found.");
                }
                break;
            case 12:
                System.out.print("Size of the array? ");
			    userInt = text.getInt();
                System.out.println("Inserting " + userInt + " numbers in intervals of 10.");
                System.out.println("Creating numbers.");
                int numbersForInserting[] = new int[userInt];
                for (int i = 0; i < numbersForInserting.length; i++) {
                    if (j % 10 == 0) {
                        numbersForInserting[i] = j;
                        j += 10;
                    }
                }
                System.out.println("Inserting into the tree.");
                for (int i = 0; i < numbersForInserting.length; i++) {
                    tree.insert(numbersForInserting[i]);
                }
                break;
            }
        }
    }
}
