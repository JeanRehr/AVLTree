public class Main {
    public static void main(String[] args) {
        AVLtree tree = new AVLtree();
        Text text = new Text();
        int j = 10;
        boolean printBeforeAfter = false;
        int userInt;
        short userOpt = 100;



        while (userOpt != 9) {
            text.options();

            userOpt = text.getUserOption((short) 15, (short) 0);

            switch (userOpt) {
            case 1: // Print Tree
                tree.printTree(tree.root);
                break;
            case 2: // Search
                System.out.print("Search which number> ");
                userInt = text.getInt();
                System.out.println((tree.search(userInt)) ? "Data found." : "Not found.");
                break;
            case 3: // Insert
                System.out.print("Insert which number> ");
                userInt = text.getInt();
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                tree.insert(userInt);
                System.out.println("Tree after operation:");
                tree.printTree(tree.root);
                break;
            case 4: // Remove
                System.out.print("Remove which number> ");
                userInt = text.getInt();
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                tree.remove(userInt);
                System.out.println("Tree after operation:");
                tree.printTree(tree.root);
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
                System.out.print("Info on which node (root is: " + tree.getRootData() + ") > ");
                userInt = text.getInt();
                Node subTreeNode = tree.searchNode(userInt);
                if (subTreeNode != null) {
                    System.out.println(
                        "Minimum of sub-tree: " +
                        tree.getMinimumData(subTreeNode) +
                        "\nMaximum of sub-tree: " +
                        tree.getMaximumData(subTreeNode) +
                        "\nRoot of the sub-tree: " +
                        userInt +
                        "\nSuccessor of sub-tree: " +
                        tree.getSuccessorData(subTreeNode) +
                        "\nPredecessor of sub-tree: " +
                        tree.getPredecessorData(subTreeNode) +
                        "\nRoot of the entire tree: " +
                        tree.getRootData()
                    );
                } else { 
                    System.out.println("Data/sub-tree not found.");
                }
                break;
            case 11:
                System.out.print("Print from which sub-tree> ");
                userInt = text.getInt();
                Node subTree = tree.searchNode(userInt);
                if (subTree != null) {
                    tree.printTree(subTree);
                } else {
                    System.out.println("Data/sub-tree not found.");
                }
                break;
            case 12:
                System.out.print("Size of the array> ");
                userInt = text.getInt();
                System.out.println("Inserting " + userInt + " numbers in intervals of 10.");
                System.out.println("Creating numbers.");
                int numbersForInserting[] = new int[userInt];
                if (j != 10) {
                    System.out.print("Press 1 to start assignment from 10> ");
                    int reset = text.getInt();
                    if (reset == 1) {
                        j = 10;
                    }
                }
                for (int i = 0; i < numbersForInserting.length; i++) {
                    if (j % 10 == 0) {
                        numbersForInserting[i] = j;
                        j += 10;
                    }
                }
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                System.out.println("Inserting into the tree.");
                for (int i = 0; i < numbersForInserting.length; i++) {
                    tree.insert(numbersForInserting[i]);
                }
                System.out.println("Tree after operation:");
                tree.printTree(tree.root);
                break;
            case 13:
                System.out.print("Delete which sub-tree> ");
                userInt = text.getInt();
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                tree.massRemove(userInt);
                System.out.println("Tree after operation:");
                tree.printTree(tree.root);
                break;
            case 14:
                printBeforeAfter = !printBeforeAfter;
                System.out.println("Print before and after operations: " + printBeforeAfter);
                break;
            }
        }
    }
}
