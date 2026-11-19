import java.util.Scanner;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Presentation layer for the waitlist app. Handles JavaFX window setup and
 * console I/O (prompts, menu, waitlist display, and terminal clearing).
 */
public class View {
    // Ansi code for wiping console.
    private static final String ANSI_WIPE = "\033[H\033[2J";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Shows the given UI root on the primary stage.
     *
     * @param stage the application window
     * @param root the loaded scene graph root
     */
    public void showGui(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("waitlist");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Reads a line from standard input after printing a {@code > } prompt.
     * Leading and trailing whitespace are removed from the result.
     *
     * @return the trimmed input line
     */
    public String readInput() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays {@code prompt}, then reads a line from standard input after
     * printing a {@code > } prompt. Leading and trailing whitespace are
     * removed from the result.
     *
     * @param prompt the message to show before reading input
     * @return the trimmed input line
     */
    public String readInput(String prompt) {
        display(prompt);
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    /**
     * Prints the given object to standard output, followed by a newline.
     *
     * @param object the value to print; may be {@code null}
     */
    public void display(Object object) {
        System.out.println(object);
    }

    /**
     * Attempts to clear the console by printing ANSI cursor-home and
     * erase-screen escape sequences.
     */
    public void clearTerminal() {
        System.out.println(ANSI_WIPE);
    }

    /**
     * Prints the main menu options (add party, wait time, seat, clear table,
     * show waitlist, show tables, quit).
     */
    public void showMenu() {
        display(
            "Restaurant Waitlist Menu"
            + System.lineSeparator()
            + "1. Add a party to waitlist"
            + System.lineSeparator()
            + "2. Get current waiting time"
            + System.lineSeparator()
            + "3. Seat next party"
            + System.lineSeparator()
            + "4. Clear a table"
            + System.lineSeparator()
            + "5. Show waitlist"
            + System.lineSeparator()
            + "6. Show tables"
            + System.lineSeparator()
            + "7. Quit"
        );
    }

    /**
     * Shows the waitlist in order, with each party's name and size.
     * Prints an empty-waitlist message when {@code waitlist} is {@code null}
     * or has length {@code 0}.
     *
     * @param waitlist the parties currently waiting, front first; may be
     *                 {@code null}
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
