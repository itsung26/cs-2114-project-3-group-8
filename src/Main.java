import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application entry point for console and GUI modes.
 */
public class Main extends Application {
    private static final boolean GUI_MODE_ON = true;

    private View view;
    private Restaurant model;
    private Controller controller;

    public static void main(String[] args) {
        if (GUI_MODE_ON) {
            launch(args);
        }
        else {
            runConsole();
        }
    }

    /**
     * Builds the MVC triad and starts the text-based menu loop.
     */
    private static void runConsole() {
        Restaurant model = new Restaurant();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }

    @Override
    public void init() {
        model = new Restaurant();
        view = new View();
        controller = new Controller(model, view);
    }

    @Override
    public void start(Stage primaryStage) {
        view.constructGui(primaryStage);
        view.clearTerminal();
    }
}
