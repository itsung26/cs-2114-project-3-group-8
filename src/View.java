import java.util.Scanner;

public class View {
    // Ansi code for wiping console.
    private static final String ANSI_WIPE = "\033[H\033[2J";
    private final Scanner scanner = new Scanner(System.in);

    public String readInput() {
        System.out.println("> ");
        return scanner.nextLine().trim();
    }

    public String readInput(String prompt) {
        display(prompt);
        System.out.println("> ");
        return scanner.nextLine().trim();
    }

    public void display(String string) {
        System.out.print(ANSI_WIPE);
        System.out.flush(); // Flush IO stream to ensure print was comitted.

        System.out.println(string);
    }

    /**
     * Clears the screen and shows the main menu options.
     */
    public void showMenu() {
        display("Restaurant Waitlist Menu"
            + System.lineSeparator()
            + "1. Add a party"
            + System.lineSeparator()
            + "2. Seat next party"
            + System.lineSeparator()
            + "3. Clear a table"
            + System.lineSeparator()
            + "4. Show waitlist"
            + System.lineSeparator()
            + "5. Show tables"
            + System.lineSeparator()
            + "6. Quit");
    }

    public void displayWaitlist(Party[] waitlist) {
        System.out.println(waitlist);
    }


}
