import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Text text = new Text();
        Scanner scanner = new Scanner(System.in);
        short choice = 0;
        AVLTreeGeneric<Integer> intTree = new AVLTreeGeneric<>();
        AVLTreeGeneric<String> stringTree = new AVLTreeGeneric<>();
        AVLTreeGeneric<Character> charTree = new AVLTreeGeneric<>();
        AVLTreeGeneric<Double> doubleTree = new AVLTreeGeneric<>();

        while (choice != 5) {
            System.out.print(
                "Choose the type of the tree:\n" +
                "1 - Integer.\n" +
                "2 - String.\n" +
                "3 - Character.\n" +
                "4 - Double.\n" +
                "5 - Exit.\n" +
                "> " 
            );
            choice = text.getShort();
            switch (choice) {
            case 1:
                interactWithTree(intTree, scanner, Integer.class, text);
                break;
            case 2:
                interactWithTree(stringTree, scanner, String.class, text);
                break;
            case 3:
                interactWithTree(charTree, scanner, Character.class, text);
                break;
            case 4:
                interactWithTree(doubleTree, scanner, Double.class, text);
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
    private static <T extends Comparable<T>> void interactWithTree(
        AVLTreeGeneric<T> tree,
        Scanner scanner,
        Class<T> type,
        Text text
    ) {
        int j = 0;
        boolean printBeforeAfter = false;
        T userValue = null;
        short userOpt = 100;

        while (userOpt != 9) {
            text.options();

            userOpt = text.getUserOption((short) 15, (short) 0);

            switch (userOpt) {
            case 1: // Print Tree
                tree.printTree(tree.root);
                break;
            case 2: // Search
                System.out.print("Enter value to search> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (userValue != null) {
                    System.out.println((tree.search(userValue)) ? "Data found." : "Not found.");
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 3: // Insert
                System.out.print("Enter value to insert> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                if (userValue != null) {
                    tree.insert(userValue);
                    System.out.println("Tree after operation:");
                    tree.printTree(tree.root);
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 4: // Remove
                System.out.print("Remove which value> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                if (userValue != null) {
                    tree.remove(userValue);
                    System.out.println("Tree after operation:");
                    tree.printTree(tree.root);
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
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
                System.out.print(
                    "Info based on which node (root is: " + 
                    ((tree.getRoot() != null) ? tree.getRootData() : "null") + ")> "
                );
                userValue = text.parseValue(scanner.nextLine(), type);
                if (userValue != null) {
                    Node<T> subTreeNode = tree.searchNode(userValue);
                    if (subTreeNode != null) {
                        System.out.println(
                            "Minimum of sub-tree: " +
                            tree.getMinimumData(subTreeNode) +
                            "\nMaximum of sub-tree: " +
                            tree.getMaximumData(subTreeNode) +
                            "\nRoot of the sub-tree: " +
                            userValue +
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
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 11:
                System.out.print("Print from which sub-tree> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (userValue != null) {
                    Node<T> subTree = tree.searchNode(userValue);
                    if (subTree != null) {
                        tree.printTree(subTree);
                    } else {
                        System.out.println("Data/sub-tree not found.");
                    }
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 12:
                T valueToBeInserted = null;
                if (Character.class.isAssignableFrom(type) || String.class.isAssignableFrom(type)) {
                    int x = 0;
                    boolean exit = false;
                    while (!exit) {
                        System.out.print(
                            "Insert:\n" +
                            "1 - Numbers.\n" +
                            "2 - Characters.\n> "
                        );
                        x = text.getInt();
                        switch (x) {
                        case 1:
                            if (j != 0) {
                                System.out.print("Press 1 to start assignment from 0> ");
                                if (text.getInt() == 1) {
                                    j = 0;
                                }
                            }
                            System.out.print("Size of the array> ");
                            int userInt = text.getInt();
                            int numbersForInserting[] = new int[userInt];
                            System.out.print("Intervals in numbers of> ");
                            int intervals = text.getInt();
                            System.out.println(
                                "Inserting " + userInt + " numbers in intervals of " + intervals + "."
                            );
                            if (printBeforeAfter) {
                                System.out.println("Tree before operation:");
                                tree.printTree(tree.root);
                            }
                            for (int i = 0; i < numbersForInserting.length; i++) {
                                valueToBeInserted = text.parseValue(String.valueOf(j), type);
                                tree.insert(valueToBeInserted);
                                j += intervals;
                            }
                            exit = true;
                            break;
                        case 2:
                            System.out.print(
                                "Insert:\n" +
                                "1 - Uppercase (default).\n" +
                                "2 - Lowercase.\n> "
                            );
                            x = text.getInt();
                            if (printBeforeAfter) {
                                System.out.println("Tree before operation:");
                                tree.printTree(tree.root);
                            }
                            if (x == 1) {
                                for (int asciiValue = 65; asciiValue < 91; asciiValue++) {
                                    char asciiChar = (char) asciiValue;
                                    valueToBeInserted = text.parseValue(String.valueOf(asciiChar), type);
                                    tree.insert(valueToBeInserted);
                                }
                            } else {
                                for (int asciiValue = 97; asciiValue < 123; asciiValue++) {
                                    char asciiChar = (char) asciiValue;
                                    valueToBeInserted = text.parseValue(String.valueOf(asciiChar), type);
                                    tree.insert(valueToBeInserted);
                                }
                            }
                            exit = true;
                            break;
                        default:
                            System.out.println("Wrong option.");
                            break;
                        }
                    }
                } else {
                    if (j != 0) {
                        System.out.print("Press 1 to start assignment from 0> ");
                        int resetJ = text.getInt();
                        if (resetJ == 1) {
                            j = 0;
                        }
                    }
                    System.out.print("Size of the array> ");
                    int userInt = text.getInt();
                    int numbersForInserting[] = new int[userInt];
                    System.out.print("Intervals in numbers of> ");
                    int intervals = text.getInt();
                    System.out.println(
                        "Inserting " + userInt + " numbers in intervals of " + intervals + "."
                    );
                    if (printBeforeAfter) {
                        System.out.println("Tree before operation:");
                        tree.printTree(tree.root);
                    }
                    for (int i = 0; i < numbersForInserting.length; i++) {
                        valueToBeInserted = text.parseValue(String.valueOf(j), type);
                        tree.insert(valueToBeInserted);
                        j += intervals;
                    }

                }
                System.out.println("Tree after operation:");
                tree.printTree(tree.root);
                break;
            case 13:
                System.out.print("Delete which sub-tree> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                if (userValue != null) {
                    tree.massRemove(userValue);
                    System.out.println("Tree after operation:");
                    tree.printTree(tree.root);
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 14:
                printBeforeAfter = !printBeforeAfter;
                System.out.println("Print before operations: " + printBeforeAfter);
                break;
            }
        }
    }
}