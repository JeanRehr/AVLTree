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
						"-----------------------------------------------------------\n" +
						"Options:" +
						"[1] Print tree.\t[6] Postorder.\n" +
						"\t[2] Search.\t[7] Inorder.\n" +
						"\t[3] Insert.\t[8] Help.\n" +
						"\t[4] Remove.\t[9] Exit.\n" +
						"\t[5] Preorder.\t[0] Clear console.\n" +
						"-----------------------------------------------------------\n");
	}

    public int getInt() {
        while (!scanner.hasNextInt()) {
    		System.out.println("Invalid number. Try again");
			scanner.next();
	  	}
        return scanner.nextInt();
    }

    public short getShort() {
        while (!scanner.hasNextShort()) {
    		System.out.println("Invalid number. Try again");
			scanner.next();
	  	}
        return scanner.nextShort();
    }

    public short getUserOption(short high, short low) {
        System.out.print("Option> ");
        short userOpt = getShort();
        while (userOpt > high || userOpt < low) {
            System.out.println("Invalid option. Try again.");
		    System.out.print("Option> ");
		    userOpt = getShort();
        }
        return userOpt;
    }

    public void help() {
        System.out.println("AVL Tree implemented in Java, works only with integers.");
    }
}
