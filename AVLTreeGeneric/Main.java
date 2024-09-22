import java.util.Scanner;
import java.io.File;
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

            userOpt = text.getUserOption((short) 1, (short) 17);

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
            case 11: // Print sub-tree
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
            case 12: // Mass Insert
                T valueToBeInserted = null;
                if (Character.class.isAssignableFrom(type)) {
                    System.out.print(
                        "Insert:\n" +
                        "1 - Numbers.\n" +
                        "2 - Characters.\n"
                    );
                    short x = text.getUserOption((short) 1, (short) 2);
                    if (x == 1) {
                        if (intervalsToInsert != 0) {
                            System.out.print("Press 1 to start assignment from 0> ");
                            if (text.getInt() == 1) {
                                intervalsToInsert = 0;
                            }
                        }
                        System.out.print("Size of the array> ");
                        int userInt = text.getInt();
                        int numbersForInserting[] = new int[userInt];
                        System.out.print("Intervals in numbers of> ");
                        int intervals = text.getInt();
                        System.out.println(
                            "Inserting " + userInt +
                            " numbers in intervals of " + intervals + "."
                        );
                        for (int i = 0; i < numbersForInserting.length; i++) {
                            valueToBeInserted = text.parseValue(String.valueOf(intervalsToInsert), type);
                            tree.insert(valueToBeInserted);
                            intervalsToInsert += intervals;
                        }
                    } else if (x == 2) {
                        System.out.print(
                            "Insert:\n" +
                            "1 - Uppercase.\n" +
                            "2 - Lowercase.\n"
                        );
                        x = text.getUserOption((short) 1, (short) 2);
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
                    }
                } else if (String.class.isAssignableFrom(type)) {
                    System.out.print(
                        "Insert:\n" +
                        "1 - Numbers.\n" +
                        "2 - Characters.\n" +
                        "3 - Words.\n"
                    );
                    short x = text.getUserOption((short) 1, (short) 3);
                    if (x == 1) {
                        if (intervalsToInsert != 0) {
                            System.out.print("Press 1 to start assignment from 0> ");
                            if (text.getInt() == 1) {
                                intervalsToInsert = 0;
                            }
                        }
                        System.out.print("Size of the array> ");
                        int userInt = text.getInt();
                        int numbersForInserting[] = new int[userInt];
                        System.out.print("Intervals in numbers of> ");
                        int intervals = text.getInt();
                        System.out.println(
                            "Inserting " + userInt +
                            " numbers in intervals of " + intervals + "."
                        );
                        for (int i = 0; i < numbersForInserting.length; i++) {
                            valueToBeInserted = text.parseValue(String.valueOf(intervalsToInsert), type);
                            tree.insert(valueToBeInserted);
                            intervalsToInsert += intervals;
                        }
                    } else if (x == 2) {
                        System.out.print(
                            "Insert:\n" +
                            "1 - Uppercase.\n" +
                            "2 - Lowercase.\n"
                        );
                        x = text.getUserOption((short) 1, (short) 2);
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
                    } else {
                        long startTime = System.nanoTime();
                        String path;
                        System.out.println(
                            "Load all English words?\n" +
                            "1 - Yes (this is not a fast operation).\n" +
                            "2 - No (will load words that starts with a and b)."
                        );
                        int opt = text.getUserOption((short) 1, (short) 2);
                        if (opt == 1) {
                            path = "all_words_less.txt";
                        } else {
                            path = "a_b_english.txt";
                        }
                            try (Stream<String> lines = Files.lines(Paths.get(path))) {
                                lines.forEach(word -> tree.insert((T) word));
                            } catch (Exception e) {
                                System.out.println("File not found or unable to read file.");
                            }
                            /*try (BufferedReader bf = new BufferedReader(new FileReader("enw.txt"))) {
                                String word;
                                while ((word = bf.readLine()) != null) {
                                    tree.insert((T) word);
                                }
                            } catch (Exception e) {
                                System.out.println("File not found.");
                            }*/
                            // unacceptable performance with scanner
                            /*try (Scanner sc = new Scanner(new File("words.txt"))) {
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
                        System.out.println("Duration: " + ((endTime - startTime) / 1000000));
                    }
                } else {
                    if (intervalsToInsert != 0) {
                        System.out.print("Press 1 to start assignment from 0> ");
                        int resetJ = text.getInt();
                        if (resetJ == 1) {
                            intervalsToInsert = 0;
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
                    for (int i = 0; i < numbersForInserting.length; i++) {
                        valueToBeInserted = text.parseValue(String.valueOf(intervalsToInsert), type);
                        tree.insert(valueToBeInserted);
                        intervalsToInsert += intervals;
                    }
                }
                break;
            case 13: // Mass Delete
                System.out.print("Delete which sub-tree> ");
                userValue = text.parseValue(scanner.nextLine(), type);
                if (printBeforeAfter) {
                    System.out.println("Tree before operation:");
                    tree.printTree(tree.root);
                }
                if (userValue != null) {
                    tree.massRemove(userValue);
                } else {
                    System.out.println("Invalid input for " + type.getSimpleName());
                }
                break;
            case 14: // Before print statements
                printBeforeAfter = !printBeforeAfter;
                System.out.println("Print before operations: " + printBeforeAfter);
                break;
            case 15: // Fuzzy search
                if (String.class.isAssignableFrom(type)) {
                    System.out.print("Search words that are similar with> ");
                    String word = scanner.nextLine();
                    System.out.print("How similar on a number scale (0 = very similar)> ");
                    int similarity = text.getInt();
                    List<String> fuzzyWords = new ArrayList<>();
                    fuzzyWords = tree.fuzzySearch(word, similarity, type);
                    System.out.println("Result:");
                    for (int i = 0; i < fuzzyWords.size(); i++) {
                        System.out.println(fuzzyWords.get(i));
                    }
                } else {
                    System.out.println("This operation only works with String trees");
                }
                break;
            case 16: // Prefix match - prints the subtree that matches a given prefix
                if (String.class.isAssignableFrom(type)) {
                    System.out.print("Search words that starts with> ");
                    tree.printTree((Node<T>) tree.prefixMatch(scanner.nextLine(), type));
                } else {
                    System.out.println("This operation only works with String trees");
                }
                break;
            }
        }
    }
}
