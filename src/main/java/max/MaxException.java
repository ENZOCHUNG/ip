package max;
/**
* Represents exceptions specific to the Max application.
 * This class is used to handle errors related to user input, parsing, and task management.
 */
public class MaxException extends Exception {

    /**
     * Constructs a new MaxException with the specified detail message.
     *
     * @param message The error message describing the failure.
     */
    public MaxException(String message) {
        super(message);
    }
}
