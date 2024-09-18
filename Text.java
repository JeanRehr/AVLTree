import java.util.Scanner;
public class Text {
    Scanner scanner = new Scanner(System.in);
    public void clearConsole() {
        /* ANSI CODE */
        // System.out.println("\033[2J\033[;H"); /* Works but not always */
        final String ANSI_CLS = "\u001b[2J"; /* Clear screen. */
        final String ANSI_HOME = "\u001b[H"; /* Cursor to the top right. */
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

    public void options() {
        System.out.print(
            "---------------------------------------------------------------------------------\n" +
            "Options:" +
            "[1] Print tree.\t[6] Postorder.\t[10] Info based on a node.\n" +
            "\t[2] Search.\t[7] Inorder.\t[11] Print a sub-tree.\n" +
            "\t[3] Insert.\t[8] Help.\t[12] Mass insert in intervals.\n" +
            "\t[4] Remove.\t[9] Exit.\t[13] Mass delete a sub-tree.\n" +
            "\t[5] Preorder.\t[0] Clearscreen.[14] Print before operations.\n" +
            "---------------------------------------------------------------------------------\n"
        );
    }

    public int getInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid number.\nNumber> ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public short getShort() {
        while (!scanner.hasNextShort()) {
            System.out.print("Invalid number.\nNumber> ");
            scanner.next();
        }
        return scanner.nextShort();
    }

    public short getUserOption(short high, short low) {
        System.out.print("Option> ");
        short userOpt = getShort();
        while (userOpt > high || userOpt < low) {
            System.out.print("Invalid option.\nOption> ");
            userOpt = getShort();
        }
        return userOpt;
    }

    public void help() {
        System.out.println("AVL Tree implemented in Java, works only with integers.");
    }
}

/*
Balance logic:
Left Left Case
Condition: The balance factor is greater than 1, indicating a left-heavy tree.
Additionally, the newly inserted data is less than the data in the left child of the node,
meaning it has been inserted into the left subtree of the left child.
Imbalance Type: Left-Left
Rotation: Right Rotation
Concept: The imbalance is corrected by performing a right rotation at the node. This
involves moving the left child up to replace the node, making the node the right child of
its previous left child. This rebalances the subtree by shifting the height distribution.

Right Right Case
Condition: The balance factor is less than -1, indicating a right-heavy tree.
Additionally, the newly inserted data is greater than the data in the right child of the
node, meaning it has been inserted into the right subtree of the right child.
Imbalance Type: Right-Right
Rotation: Left Rotation
Concept: The imbalance is corrected by performing a left rotation at the node. This
involves moving the right child up to replace the node, making the node the left child of
its previous right child. This rebalances the subtree by shifting the height distribution.

Left Right Case
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

Right Left Case
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

Successor/antecessor logic:
successor logic when given node doesn't has a right tree
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
