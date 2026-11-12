import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Application entry point for console and GUI modes.
 * Acts as the composition root: it constructs the {@link Restaurant} model,
 * {@link View}, and {@link Controller}, then either starts the console menu
 * loop or launches JavaFX and wires the controller into the FXML loader.
 */
public class Main extends Application {
    /**
     * When {@code true}, starts the JavaFX GUI; when {@code false}, runs the
     * text-based menu instead.
     */
    private static final boolean GUI_MODE_ON = false;

    private View view;
    private Restaurant model;
    private Controller controller;

    /**
     * Chooses console or GUI mode based on {@link #GUI_MODE_ON}.
     *
     * @param args command-line arguments (unused; forwarded to JavaFX when
     *             GUI mode is on)
     */
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
        Restaurant model = new Restaurant("Test Restaurant");
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }

    /**
     * Creates the model, view, and controller used by the JavaFX application.
     * Runs before {@link #start(Stage)}.
     */
    @Override
    public void init() {
        model = new Restaurant("Test Restaurant");
        view = new View();
        controller = new Controller(model, view);
    }

    /**
     * Loads the FXML layout, attaches the existing {@link Controller}, and
     * asks the {@link View} to show it on the primary stage.
     * On load failure, prints the stack trace and exits the JavaFX runtime.
     *
     * @param primaryStage the main application window provided by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        System.out.println("hello world");
        try {
            FXMLLoader guiLoader = new FXMLLoader(getGuiBlueprintUrl());
            guiLoader.setController(controller); // Required for @FXML to work in the controller.
            Parent root = guiLoader.load();
            view.showGui(primaryStage, root);
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
            return;
        }
        view.clearTerminal();
    }

    /**
     * Called when the JavaFX application is shutting down.
     *
     * @throws Exception if shutdown work fails
     */
    @Override
    public void stop() throws Exception {
        System.out.println("goodbye world");
    }

    /**
     * Resolves the FXML layout file used to build the GUI.
     * Looks for {@code assets/ui-blueprint/ui-blueprint.fxml} relative to
     * the process working directory.
     *
     * @return a {@link URL} pointing to the FXML file
     * @throws FileNotFoundException if the file is missing or cannot be
     *         converted to a URL
     */
    private URL getGuiBlueprintUrl() throws FileNotFoundException {
        File file = new File("assets/ui-blueprint/ui-blueprint.fxml");
        if (!file.exists()) {
            throw new FileNotFoundException("Failed to fetch ui blueprint file.");
        }

        try {
            // Convert the File object to a URL pointing to a file.
            // A URL must be passed to the fxml loader (the ui loader) for
            // the UI to be loaded from the fxml.
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Failed to fetch ui blueprint file.");
        }
    }
}
