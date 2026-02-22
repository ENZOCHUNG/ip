package max;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Max max;
    private Ui ui = new Ui();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/gojo.jpeg"));
    private Image maxImage = new Image(this.getClass().getResourceAsStream("/images/jogo.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().add(
            DialogBox.getMaxDialog(ui.getWelcomeMessage(), maxImage)
        );
    }

    /** Injects the Max instance */
    public void setMax(Max m) {
        max = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = max.getResponse(input);

        System.out.println("DEBUG input=" + input + " | isExit=" + max.isExit());

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMaxDialog(response, maxImage)
        );
        userInput.clear();
        if (max.isExit()) {
            System.out.println("DEBUG exiting now");
            Platform.exit();
        }
    }
}
