import java.util.Scanner;

public class View {
    // Ansi code for wiping console.
    private static final String ANSI_WIPE = "\033[H\033[2J";
    private final Scanner scanner = new Scanner(System.in);

    public String readInput() {
        System.out.println("> ");
        return scanner.nextLine().trim();
    }

    public void display(String string) {
        System.out.print(ANSI_WIPE);
        System.out.flush(); // Flush IO stream to ensure print was comitted.

        System.out.println(string);
    }

    public void displayWaitlist(Queue<Party> waitlist) {
        // Party[] snapshot = waitlist.toArray();
    }


}
