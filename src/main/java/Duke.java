import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    final static ArrayList<Task> tasks = new ArrayList<>();

    final static String indent = "    ";
    final static String textIndent = "     ";
    final static String divide = indent + "____________________________________________________________\n";
    final static String welcomeMessage = textIndent + "Hello! I'm Duke\n" + textIndent + "What can I do for you?\n";
    final static String exitMessage = textIndent + "Bye. Hope to see you again soon!\n";
    final static String listMessage = textIndent + "Here are the tasks in your list:\n";
    final static String doneMessage = textIndent + "Nice! I've marked this task as done:\n";
    final static String todoMessage = textIndent + "Got it. I've added this task:\n";

    static String[] commands = {"bye", "list", "done", "todo", "deadline", "event"};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        say(welcomeMessage);

        int index = 0;
        String input;
        while(!(input = sc.nextLine()).equals("bye")){

            try {
                handleCommand(input);
            } catch (DukeException e) {
                say(textIndent + e.getMessage() + "\n");
            }
        }

        say(exitMessage);
        sc.close();

    }

    public static void say(String msg) {
        System.out.println(divide + msg + divide);
    }

    public static void handleCommand(String input) throws DukeException{
        String[] commandDetail = input.split(" ", 2);

        if (commandDetail.length == 0) {
            throw new DukeException("Be sure to follow the exact format of the commands!");
        }

        String command = commandDetail[0];
        String detail;

        switch (command) {
            case "list":
                handleList();
                break;

            case "done":
                if (commandDetail.length < 2) {
                    throw new DukeException("Please key in a task to be marked as done!");
                }
                detail = commandDetail[1];
                handleDone(detail);
                break;

            case "todo":
                if (commandDetail.length < 2) {
                    throw new DukeException("Please input a task to add!");
                }
                detail = commandDetail[1];
                handleTodo(detail);
                break;

            case "deadline":
                if (commandDetail.length < 2) {
                    throw new DukeException("Please input a task to add!");
                }
                detail = commandDetail[1];
                handleDeadline(detail);
                break;

            case "event":
                if (commandDetail.length < 2) {
                    throw new DukeException("Please input a task to add!");
                }
                detail = commandDetail[1];
                handleEvent(detail);
                break;

            default:
                throw new DukeException("Sorry! I don't recognize that command!");
        }
    }

    public static void handleList() {
        StringBuilder out = new StringBuilder(listMessage);
        for (int i = 0; i < tasks.size(); i++) {
            String nextLine = textIndent + (i + 1) +"." + tasks.get(i) + "\n";
            out.append(nextLine);
        }
        say(out.toString());
    }

    public static void handleDone(String detail) throws DukeException {
        try {
            int num = Integer.parseInt(detail);
            Task curr = tasks.get(num - 1);
            curr.markAsDone();
            say(doneMessage + textIndent + curr + "\n");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please key in the number of an existing task to be marked as done!");
        }
    }

    public static void handleTodo(String detail) {
        Task task = new Todo(detail);
        tasks.add(task);
        String numTasks = "Now you have " + tasks.size() + " tasks in the list.\n";
        String out = todoMessage + textIndent + task +
                "\n" + textIndent + numTasks;
        say(out);
    }

    public static void handleDeadline(String detail) throws DukeException {
        try {
            String[] split = detail.split(" /by", 2);
            Task task = new Deadline(split[0], split[1]);
            tasks.add(task);
            String numTasks = "Now you have " + tasks.size() + " tasks in the list.\n";
            String out = todoMessage + textIndent + task +
                    "\n" + textIndent + numTasks;
            say(out);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Please enter a deadline to complete the task by!");
        }
    }

    public static void handleEvent(String detail) throws DukeException {
        try {
            String[] split = detail.split(" /at", 2);
            Task task = new Event(split[0], split[1]);
            tasks.add(task);
            String numTasks = "Now you have " + tasks.size() + " tasks in the list.\n";
            String out = todoMessage + textIndent + task +
                    "\n" + textIndent + numTasks;
            say(out);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Please enter the time at which the event will take place!");
        }
    }
}
