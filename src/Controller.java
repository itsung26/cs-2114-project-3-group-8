import java.util.List;
class Controller {
    private final Restaurant model;
    private final View view;
    public Controller(Restaurant model, View view) {
        if (model == null || view == null) {
            throw new IllegalArgumentException(
                "Model and view must not be null.");
        }
        this.model = model;
        this.view = view;
    }

    
    public void run() {
        boolean running = true;
        while (running) {
            view.showMenu();
            int choice = readInt("Choose an option", 1, 6);
            if (choice == 1) {
                addParty();
            }
            else if (choice == 2) {
                seatNextParty();
            }
            else if (choice == 3) {
                clearTable();
            }
            else if (choice == 4) {
                showWaitlist();
            }
            else if (choice == 5) {
                showTables();
            }
            else if (choice == 6) {
                running = false;
            }
        }
        view.showMessage("Goodbye!");
    }


    public void addParty() {
        if (model.getWaitlistSnapshot().size() >= 20) {
            view.showMessage("The waitlist is full (max " + 20
                + " parties). Seat a party before adding another.");
            return;
        }
        String name = readNonEmptyString("Enter party name");
        int size = readInt("Enter party size", 1, 8);
        try {
            model.addParty(new Party(name, size));
            view.showMessage("Added party \"" + name + "\" (size " + size
                + ") to the waitlist.");
        }
        catch (IllegalArgumentException e) {
            view.showMessage("Could not add party: " + e.getMessage());
        }
    }


    public void seatNextParty() {
        if (model.seatNextParty()) {
            view.showMessage("The next party has been seated.");
        }
        else if (model.getWaitlistSnapshot().isEmpty()) {
            view.showMessage("There is no party to seat in the waitlist.");
        }
        else {
            view.showMessage("No tables are available right now.");
        }
    }


    public void clearTable() {
        int numTables = model.getTables().size();
        int tableNumber = readInt("Enter the table number to clear", 1,
            numTables);

        if (model.clearTable(tableNumber)) {
            view.showMessage("Table " + tableNumber
                + " is now clean and available.");
        }
        else {
            view.showMessage("Table " + tableNumber + " is already empty.");
        }
    }


    public void showWaitlist() {
        List<Party> waitlist = model.getWaitlistSnapshot();
        if (waitlist.isEmpty()) {
            view.showMessage("The waitlist is empty.");
        }
        else {
            view.showWaitlist(waitlist);
        }
    }


    public void showTables() {
        view.showTables(model.getTables());
    }


    private int readInt(String prompt, int min, int max) {
        String fullPrompt = prompt + " (" + min + "-" + max + ")";
        while (true) {
            String input = view.readLine(fullPrompt);
            try {
                int value = Integer.parseInt(input.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                view.showMessage("Input must be between " + min + " and " + max
                    + ".");
            }
            catch (NumberFormatException e) {
                view.showMessage("Please enter a whole number between " + min
                    + " and " + max + ".");
            }
        }
    }


    private String readNonEmptyString(String prompt) {
        while (true) {
            String input = view.readLine(prompt);
            if (input.isBlank()) {
                view.showMessage("Entry cannot be empty. Please try again.");
            }
            else {
                return input.trim();
            }
        }
    }
}
