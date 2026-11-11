public class View {
    // Ansi code for wiping console.
    private static final String ANSI_WIPE = "\033[H\033[2J";

    public void print(String string) {
        System.out.print(ANSI_WIPE);
        // Flush IO stream to ensure print was comitted.
        System.out.flush();

        System.out.println(string);
    }

    public void displayWaitlist() {

    }
}
