package max;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import max.command.ByeCommand;
import max.command.Command;
import max.command.ListCommand;
import max.command.MarkCommand;
import max.command.UnmarkCommand;
import max.command.EventCommand;
import max.command.TodoCommand;
import max.command.DeleteCommand;
import max.command.DeadlineCommand;
import max.command.FindCommand;

/**
 * Deciphers user input and translates it into specific Command objects.
 * This class handles string manipulation and validation to ensure the application
 * receives clean data.
 */
public class Parser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * Parses the full user input and determines which command to execute.
     *
     * @param fullCommand The raw input string entered by the user.
     * @return A specific Command object corresponding to the user's request.
     * @throws MaxException If the command is empty, unknown, or incorrectly formatted.
     */
    public static Command parse(String fullCommand) throws MaxException {
        String trimmed = fullCommand.trim();

        if (trimmed.isEmpty()) {
            throw new MaxException("Please enter a command.");
        }
        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
        case "list":
            return new ListCommand();

        case "bye":
            return new ByeCommand();

        case "mark":
            return parseMark(arguments);

        case "unmark":
            return parseUnmark(arguments);

        case "delete":
            return parseDelete(arguments);

        case "todo":
            return parseTodo(arguments);

        case "deadline":
            return parseDeadline(arguments);

        case "event":
            return parseEvent(arguments);

        case "find":
            return parseFind(arguments);

        default:
            throw new MaxException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses arguments for a mark command.
     * * @param arguments The task index provided by the user.
     * @return A MarkCommand for the specified task.
     * @throws MaxException If the index is not a valid integer.
     */
    private static Command parseMark(String arguments) throws MaxException {
        try {
            int index = Integer.parseInt(arguments.trim()) - 1;
            return new MarkCommand(index);
        } catch (NumberFormatException e) {
            throw new MaxException("Write in this format: \"mark [number]\"");
        }
    }

    /**
     * Parses arguments for an unmark command.
     * * @param arguments The task index provided by the user.
     * @return An UnmarkCommand for the specified task.
     * @throws MaxException If the index is not a valid integer.
     */
    private static Command parseUnmark(String arguments) throws MaxException {
        try {
            int index = Integer.parseInt(arguments.trim()) - 1;
            return new UnmarkCommand(index);
        } catch (NumberFormatException e) {
            throw new MaxException("Write in this format: \"unmark [number]\"");
        }
    }

    /**
     * Parses arguments for a delete command.
     * * @param arguments The task index provided by the user.
     * @return A DeleteCommand for the specified task.
     * @throws MaxException If the index is not a valid integer.
     */
    private static Command parseDelete(String arguments) throws MaxException {
        try {
            int index = Integer.parseInt(arguments.trim()) - 1;
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            throw new MaxException("Write in this format: \"delete [number]\"");
        }
    }

    /**
     * Validates and creates a TodoCommand.
     * * @param arguments The description of the todo task.
     * @return A TodoCommand with the given description.
     * @throws MaxException If the description is empty.
     */
    private static Command parseTodo(String arguments) throws MaxException {
        if (arguments.trim().isEmpty()) {
            throw new MaxException("The description of a todo cannot be empty.");
        }
        return new TodoCommand(arguments.trim());
    }

    /**
     * Parses the description and date for a deadline task.
     * * @param arguments Input containing description and "/by" date.
     * @return A DeadlineCommand.
     * @throws MaxException If the date format is invalid or "/by" is missing.
     */
    private static Command parseDeadline(String arguments) throws MaxException {
        try {
            String[] parts = arguments.split(" /by", 2);
            String description = parts[0].trim();
            String date = parts[1].trim();
            LocalDate myDate = LocalDate.parse(date, Parser.formatter);
            return new DeadlineCommand(description, myDate);
        } catch (DateTimeParseException e) {
            throw new MaxException("Invalid date. Write in this format: \"deadline [task description] /by [time description]\"");
        }
    }

    /**
     * Parses the description and date range for an event task.
     * * @param arguments Input containing description, "/from" date, and "/to" date.
     * @return An EventCommand.
     * @throws MaxException If dates are invalid or delimiters are missing.
     */
    private static Command parseEvent(String arguments) throws MaxException {
        try {
            String[] parts = arguments.split(" /from", 2);
            String description = parts[0].trim();
            String time = parts[1].trim();
            String[] parts2 = time.split(" /to");
            String from = parts2[0].trim();
            String to = parts2[1].trim();
            LocalDate fromDate = LocalDate.parse(from, Parser.formatter);
            LocalDate toDate = LocalDate.parse(to, Parser.formatter);
            return new EventCommand(description, fromDate, toDate);
        } catch (DateTimeParseException e) {
            throw new MaxException("Invalid date. Write in this format: \"event [task description] /from yyyy-MM-dd /to yyyy-MM-dd\"");
        }
    }

    /**
     * Parses a search query for the find command.
     * * @param arguments The keyword to search for.
     * @return A FindCommand with the search string.
     */
    private static Command parseFind(String arguments) throws MaxException {
        try {
            String str = arguments;
            return new FindCommand(str);
        } catch (NumberFormatException e) {
            throw new MaxException("Write in this format: \"event [task description] /from yyyy-MM-dd /to yyyy-MM-dd\"");
        }
    }
}
