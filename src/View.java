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

    /**
     * Shows the waitlist in order, with each party's name and size.
     *
     * @param waitlist the parties currently waiting, front first
     */
    public void displayWaitlist(Party[] waitlist) {
        if (waitlist == null || waitlist.length == 0) {
            display("The waitlist is empty.");
            return;
        }

        StringBuilder output = new StringBuilder("Current Waitlist:");
        for (int i = 0; i < waitlist.length; i++) {
            Party party = waitlist[i];
            output.append(System.lineSeparator())
                .append(i + 1)
                .append(". ")
                .append(party.getName())
                .append(" (")
                .append(party.getSize())
                .append(party.getSize() == 1 ? " person)" : " people)");
        }
        display(output.toString());
    }


}
