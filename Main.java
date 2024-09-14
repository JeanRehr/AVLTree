public class Main {
    public static void main(String[] args) {
        AVLtree tree = new AVLtree();
        Text text = new Text();

        int userInput;
        short userOpt = 10;

        while (userOpt != 9) {
			text.options();

            userOpt = text.getUserOption((short) 9, (short) 0);

            switch (userOpt) {
            case 1: // Print Tree
                tree.printTree();
                break;
            case 2: // Search
                System.out.print("Search which number? ");
                userInput = text.getInt();
                System.out.println(tree.search(userInput));
                break;
            case 3: // Insert
                System.out.print("Insert which number? ");
			    userInput = text.getInt();
                tree.insert(userInput);
                tree.printTree();
                break;
            case 4: // Remove
                System.out.print("Remove which number? ");
			    userInput = text.getInt();
                tree.remove(userInput);
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
            }
        }
    }
}
