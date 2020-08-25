package duke;

import duke.command.*;
import duke.exception.DukeException;

public class Parser {

    public static Command parse(String input) throws DukeException {
        String[] commandDetail = input.split(" ", 2);

        if (commandDetail.length == 0) {
            throw new DukeException("Be sure to follow the exact format of the commands!");
        }

        Commands command;
        try {
            command = Commands.valueOf(commandDetail[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException("Sorry! I don't recognize that command!");
        }

        switch (command) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand();
        case FIND:
            if (commandDetail.length < 2) {
                throw new DukeException("Please key in a task to find!");
            }
            return new FindCommand(commandDetail[1]);
        case DONE:
            if (commandDetail.length < 2) {
                throw new DukeException("Please key in a task to be marked as done!");
            }
            return new DoneCommand(commandDetail[1]);
        case DELETE:
            if (commandDetail.length < 2) {
                throw new DukeException("Please key in a task to delete!");
            }
            return new DeleteCommand(commandDetail[1]);
        case TODO:
        case DEADLINE:
        case EVENT:
            if (commandDetail.length < 2) {
                throw new DukeException("Please input a task to add!");
            }
            return new AddCommand(command, commandDetail[1]);
        default:
            throw new DukeException("Sorry! I don't recognize that command!");
        }
    }
}
