import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

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
                "5 - Exit.\n" 
            );
            choice = text.getUserOption((short) 1, (short) 5);
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
        int intervalsToInsert = 0;
        boolean printBeforeAfter = false;
        T userValue = null;
        short userOpt = 100;

        while (userOpt != 9) {
            text.options();

            userOpt = text.getUserOption((short) 0, (short) 17);

            switch (userOpt) {
            case 1: // Print Tree
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }
                tree.printTree(tree.getRoot());
                break;
            case 2: // Search
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                System.out.print("Enter value to search> ");
                userValue = text.parseValue(scanner.nextLine(), type);

                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }

                System.out.println((tree.search(userValue)) ? "Data found." : "Not found.");   
                break;
            case 3: // Insert
                System.out.print("Enter value to insert> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.getRoot());
                }
                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }
        
                tree.insert(userValue);
                System.out.println("Tree after operation:");
                tree.printTree(tree.getRoot());        
                break;
            case 4: // Remove
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }
                System.out.print("Remove which value> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.getRoot());
                }
                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }

                tree.remove(userValue);
                System.out.println("Tree after operation:");
                tree.printTree(tree.getRoot());
                break;
            case 5: // Preorder
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }
                tree.preorder();
                break;
            case 6: // Postorder
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }
                tree.postorder();
                break;
            case 7: // Inorder
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }
                tree.inorder();
                break;
            case 8: // Help
                System.out.println(
                    "AVL Tree implemented in Java, currently, this tree is of type: " + type + "."
                );
                break;
            case 9: // Exit
                break;
            case 0: // Clear Console
                text.clearConsole();
                break;
            case 10: // Print Info
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                System.out.print(
                    "Info based on which node (root is: " +
                    tree.nodeDataToString(tree.getRoot()) + ")> "
                );
                userValue = text.parseValue(scanner.nextLine(), type);

                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }

                Node<T> subTreeNode = tree.searchNode(userValue);

                if (subTreeNode == null) {
                    System.out.println("Data/sub-tree not found.");
                    break;
                }

                System.out.println(
                    "Minimum of sub-tree: " +
                    tree.nodeDataToString(tree.minimum(subTreeNode)) +
                    "\nMaximum of sub-tree: " +
                    tree.nodeDataToString(tree.maximum(subTreeNode)) +
                    "\nRoot of the sub-tree: " +
                    userValue +
                    "\nSuccessor of sub-tree: " +
                    tree.nodeDataToString(tree.successor(subTreeNode)) +
                    "\nPredecessor of sub-tree: " +
                    tree.nodeDataToString(tree.predecessor(subTreeNode)) +
                    "\nRoot of the entire tree: " +
                    tree.nodeDataToString(tree.getRoot()) +
                    "\nTotal nodes in this sub-tree: " +
                    tree.getTotalNodes(subTreeNode)
                );
                break;
            case 11: // Print sub-tree
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                System.out.print("Print from which sub-tree> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                
                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }

                Node<T> subTree = tree.searchNode(userValue);

                if (subTree == null) {
                    System.out.println("Data/sub-tree not found.");
                    break;
                }

                tree.printTree(subTree);
                break;
            case 12: // Mass Insert
                if (String.class.isAssignableFrom(type)) {
                    System.out.print(
                        "Insert:\n" +
                        "1 - Numbers.\n" +
                        "2 - Characters.\n" +
                        "3 - Words.\n"
                    );
                    short insertOption = text.getUserOption((short) 1, (short) 3);
                    if (insertOption == 1) {
                        massInsertNumbers(text, tree, type);
                    } else if (insertOption == 2) {
                        massInsertChar(text, tree, type);
                    } else {
                        massInsertWords(text, tree, type, scanner);
                    }
                } else if (Character.class.isAssignableFrom(type)) {
                    System.out.print(
                        "Insert:\n" +
                        "1 - Numbers.\n" +
                        "2 - Characters.\n"
                    );
                    short insertOption = text.getUserOption((short) 1, (short) 2);
                    if (insertOption == 1) {
                        massInsertNumbers(text, tree, type);
                    } else {
                    massInsertChar(text, tree, type);
                    }
                } else {
                    massInsertNumbers(text, tree, type);
                }
                break;
            case 13: // Mass Delete
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                System.out.print("Delete which sub-tree (Root is: " + tree.nodeDataToString(tree.getRoot()) + ")> ");
                
                userValue = text.parseValue(scanner.nextLine(), type);
                
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.getRoot());
                }

                if (userValue == null) {
                    System.out.println("Invalid input for " + type.getSimpleName());
                    break;
                }

                tree.massRemove(userValue);
                break;
            case 14: // Before print statements
                printBeforeAfter = !printBeforeAfter;
                System.out.println("Print before operations: " + printBeforeAfter);
                break;
            case 15: // Prefix match - prints the subtree that matches a given prefix
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                if (!String.class.isAssignableFrom(type)) {
                    System.out.println("This operation only works with String trees");
                    break;
                }

                System.out.print("Search words that starts with (case-sensitive)> ");
                tree.printTree((Node<T>) tree.prefixMatch(scanner.nextLine(), type));
                break;
            case 16: // Fuzzy search
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                if (!String.class.isAssignableFrom(type)) {
                    System.out.println("This operation only works with String trees");
                    break;
                }
                
                System.out.print("Search words that are similar with (case-sensitive)> ");
                String word = scanner.nextLine();
                
                System.out.print("How similar on a number scale (0 = very similar)> ");
                int similarity = text.getInt();
                
                List<String> fuzzyWords = new ArrayList<>();
                
                fuzzyWords = tree.fuzzySearch(word, similarity, type);

                System.out.println("Result:");
                for (int i = 0; i < fuzzyWords.size(); i++) {
                    System.out.println(fuzzyWords.get(i));
                }
                break;
            case 17: // Manually walk the tree.
                if (isTreeEmpty(tree)) {
                    System.out.println("Tree is empty.");
                    break;
                }

                Node<T> currentNode = tree.getRoot(); // starts at root

                short opt = 0;
                while (opt != 4) {
                    treeRepresentation(currentNode, tree);
    
                    System.out.print(
                        "Walk:\n" +
                        "1 - Left.\n" +
                        "2 - Up.\n" +
                        "3 - Right.\n" +
                        "4 - Exit.\n"
                    );

                    opt = text.getUserOption((short) 1, (short) 4);
                    
                    currentNode = walk(tree, currentNode, opt); // current node gets assigned
                }                                               // to a direction depending on opt
            }
        }
    }

    public static boolean isTreeEmpty(AVLTreeGeneric tree) {
        return tree.getRoot() == null ? true : false;
    }

    // Representing the tree with parent, brother and children
    public static <T extends Comparable<T>> void treeRepresentation(
        Node<T> node,
        AVLTreeGeneric<T> tree
    ) {
        System.out.print("\t  " + tree.nodeDataToString(node.parent) + "\n");
        if (node.parent == null) {
            System.out.print("\n          " + tree.nodeDataToString(node));
            System.out.print("        H: " + tree.getHeight(node));
        } else {
            if (node.parent.left == node && node.parent.right != null) {
                System.out.print("\t  | \\\n");
                System.out.print("          " + tree.nodeDataToString(node));
                System.out.print("  " + tree.nodeDataToString(node.parent.right));
                System.out.print("            H: " + tree.getHeight(node));
            } else if (node.parent.right == node && node.parent.left != null) {
                System.out.print("\t /|\n");
                System.out.print("       " + tree.nodeDataToString(node.parent.left));
                System.out.print(" " + tree.nodeDataToString(node));
                System.out.print("            H: " + tree.getHeight(node));
            } else {
                System.out.print("\t  |\n");
                System.out.print("         " + tree.nodeDataToString(node));
                System.out.print("            H: " + tree.getHeight(node));
            }
        }
        System.out.print("\n");
        if (node.left != null && node.right != null) {
            System.out.print("\t /  \\\n");
        } else if (node.left != null && node.right == null) {
            System.out.print("\t /\n");
        } else if (node.left == null && node.right != null) {
            System.out.print("\t   \\\n");
        } else {
            System.out.print("\n");
        }
        System.out.print("\t" + tree.nodeDataToString(node.left));
        System.out.print("   " + tree.nodeDataToString(node.right) + "\n");
    }

    // Returns the current node where it is going
    public static <T extends Comparable<T>> Node<T> walk(
        AVLTreeGeneric<T> tree,
        Node<T> node,
        short opt
    ) {
        if (opt == 1) {
            if (node.left == null) {
                System.out.println("*** Left is null. ***");
                return node;
            }
            return node.left;
        } else if (opt == 2) {
            if (node.parent == null) {
                System.out.println("*** Parent is null. ***");
                return node;
            }
            return node.parent;
        } else if (opt == 3) {
            if (node.right == null) {
                System.out.println("*** Right is null. ***");
                return node;
            }
            return node.right;
        }
        return node;
    }

    public static <T extends Comparable<T>> void massInsertNumbers(
        Text text,
        AVLTreeGeneric<T> tree,
        Class<T> type
        ) {
        T valueToBeInserted = null;
        System.out.print("Start the insertion at what number> ");
        int startNumber = text.getInt();
        System.out.print("Max number to stop insertions> ");
        int endNumber = text.getInt();
        int intervals = 0;
        while (intervals == 0) {
            System.out.print("Intervals in numbers of> ");
            intervals = text.getInt();
            if (intervals == 0) {
                System.out.println("Intervals can't be 0.");
            }
        }
        System.out.println(
            "Inserting " + startNumber +
            " to " + endNumber +
            " in intervals of " + intervals + "."
        );
        for (int i = startNumber; i <= endNumber; i = i + intervals) {
            valueToBeInserted = text.parseValue(String.valueOf(i), type);
            tree.insert(valueToBeInserted);
        }
    }

    public static <T extends Comparable <T>> void massInsertChar(
        Text text,
        AVLTreeGeneric<T> tree,
        Class<T> type
    ) {
        T valueToBeInserted = null;
        
        System.out.print(
            "Insert:\n" +
            "1 - Uppercase.\n" +
            "2 - Lowercase.\n"
        );
        int opt = text.getUserOption((short) 1, (short) 2);
        if (opt == 1) {
            for (int asciiValue = 65; asciiValue < 91; asciiValue++) {
                char asciiChar = (char) asciiValue;
                valueToBeInserted = text.parseValue(String.valueOf(asciiChar), type);
                tree.insert(valueToBeInserted);
            }
        } else if (opt == 2) {
            for (int asciiValue = 97; asciiValue < 123; asciiValue++) {
                char asciiChar = (char) asciiValue;
                valueToBeInserted = text.parseValue(String.valueOf(asciiChar), type);
                tree.insert(valueToBeInserted);
            }
        }
    }

    public static <T extends Comparable<T>> void massInsertWords(
        Text text,
        AVLTreeGeneric<T> tree,
        Class<T> type,
        Scanner scanner
    ) {
        if (!String.class.isAssignableFrom(type)) {
            System.out.println("Only String Trees are accepted for this operation.");
            return;
        }
        
        String path;

        System.out.println(
            "Load 274926 English words?\n" +
            "1 - Yes (this is not a fast operation).\n" +
            "2 - No (will load the words that starts with a and b).\n" +
            "3 - Load a file of words (must be separated by new lines).\n" +
            "4 - Cancel."
        );

        int opt = text.getUserOption((short) 1, (short) 4);

        if (opt == 1) {
            path = "all_words_less.txt";
        } else if (opt == 2) {
            path = "a_b_english.txt";
        } else if (opt == 3) {
            System.out.print("File name> ");
            path = scanner.nextLine();
        } else {
            return;
        }

        long startTime = System.nanoTime();

        try (Stream<String> lines = Files.lines(Paths.get(path))) {        
            lines.forEach(word -> tree.insert((T) word));
        } catch (Exception e) {
            System.out.println("File not found or unable to read file.");
        }
        /*try (BufferedReader bf = new BufferedReader(new FileReader("enw.txt"))) {
            long startTime = System.nanoTime();
            String word;
            while ((word = bf.readLine()) != null) {
                tree.insert((T) word);
            }
        } catch (Exception e) {
            System.out.println("File not found.");
        }*/
        // unacceptable performance with scanner
        /*try (Scanner sc = new Scanner(new File("words.txt"))) {
            long startTime = System.nanoTime();
            sc.useDelimiter("(\\n)");
            while(sc.hasNext()) {
                String word = sc.next();
                if (word.length() > 0) {
                    tree.insert((T) word);
                }
            }
        } catch (Exception e) {
            System.out.println("File not found.");
        }*/
        long endTime = System.nanoTime();
        System.out.println("Duration: " + ((endTime - startTime) / 1000000) + "ms");
    }
}
