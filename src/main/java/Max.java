import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class Max {
    private String name = "Max";
    private String seperator = "-------------------------------------------";
    private Scanner scanner = new Scanner(System.in);
    private String input;
//private List<String> list = new ArrayList<>();
    private TodoList todoList = new TodoList();

    public String getName() {
        return this.name;
    }

    public void greet() {
        System.out.println(seperator);
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
        System.out.println(seperator);
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(seperator);
    }

    public void saveTxt() {
        File file = new File("data/Max.txt" );
        File folder = file.getParentFile();   
        if (folder != null && folder.isDirectory() && !file.exists()) {
            System.out.println("Folder missing. Creating: " + folder.getName());
            folder.mkdirs();
        } 
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(todoList);
        } catch (IOException e) {
            System.err.println("Could not save file: " + e.getMessage());
        }
    }
    
    public void startConversation() {
        input = scanner.nextLine();
        //String lowerInput = input.toLowerCase();

        while(!input.equalsIgnoreCase("bye")) {
            if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                System.out.println(this.todoList);
                input = scanner.nextLine();
            }
            else if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            }
            else if (input.startsWith("mark ")) {
                try {
                    String num = input.substring(5).trim(); 
                    int index = Integer.parseInt(num) - 1;
    
                    if (index < 0 || index >= todoList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    todoList.markTask(index);        
                    System.out.println(seperator);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(this.todoList.getTask(index));
                    System.out.println(seperator);
                    saveTxt();
                    input = scanner.nextLine();

                } catch (NumberFormatException e){
                    System.out.println("Write in this format: \"mark [number]\"");
                    input = scanner.nextLine();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Number keyed in is out of bound. You have " + todoList.getTaskLength() + " task(s)");
                    input = scanner.nextLine();
                }
            }
            else if (input.startsWith("unmark ")) {
                try {
                    String num = input.substring(7).trim(); 
                    int index = Integer.parseInt(num) - 1;
    
                    if (index < 0 || index >= todoList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    todoList.unmarkTask(index);     
                    System.out.println(seperator);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(this.todoList.getTask(index));
                    System.out.println(seperator);
                    saveTxt();
                    input = scanner.nextLine();
                } catch (NumberFormatException e){
                    System.out.println("Write in this format: \"unmark [number]\"");
                    input = scanner.nextLine();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Number keyed in is out of bound. You have " + todoList.getTaskLength() + " task(s)");
                    input = scanner.nextLine();
                }  
            }
            else if (input.startsWith("delete ")) {
                try {
                    String num = input.substring(7).trim(); 
                    int index = Integer.parseInt(num) - 1;
    
                    if (index < 0 || index >= todoList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    System.out.println(seperator);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(this.todoList.getTask(index));
                    todoList.removeTask(index);   
                    System.out.println("Now you have " + this.todoList.getTaskLength() + " tasks in the list.");
                    System.out.println(seperator);
                    saveTxt();
                    input = scanner.nextLine();

                } catch (NumberFormatException e){
                    System.out.println("Write in this format: \"delete [number]\"");
                    input = scanner.nextLine();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Number keyed in is out of bound. You have " + todoList.getTaskLength() + " task(s)");
                    input = scanner.nextLine();
                }
            }
            else if (input.startsWith("todo ")) {
                try {
                    String[] parts = input.split(" ", 2);
                    String description = parts[1];
                    todoList.addTask(description);
                    saveTxt();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Write in this format: \"todo [task description]\"");
                    input = scanner.nextLine();
                }
                System.out.println("Got it. I've added this task: \n" + todoList.getLastTask() + "\n" + "Now you have " + todoList.getTaskLength() + " tasks in the list.\n");
                input = scanner.nextLine();
            }

            else if (input.startsWith("deadline ")) {
                try {
                    String descriptionAndDate = input.substring(9);
                    String[] parts = descriptionAndDate.split(" /by", 2);
                    String description = parts[0].trim();
                    String date = parts[1].trim();
                    todoList.addTask(description, date);
                    saveTxt();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Write in this format: \"deadline [task description] /by [time description]\"");
                    input = scanner.nextLine();
                }
                System.out.println("Got it. I've added this task: \n" + todoList.getLastTask() + "\n" + "Now you have " + todoList.getTaskLength() + " tasks in the list.\n");
                input = scanner.nextLine();
            }
            
            else if (input.startsWith("event ")) {
                try {
                    String descriptionAndDate = input.substring(6);
                    String[] parts = descriptionAndDate.split(" /from", 2);
                    String description = parts[0].trim();
                    String time = parts[1].trim();
                    String[] parts2 = time.split(" /to");
                    String from = parts2[0].trim();
                    String by = parts2[1].trim();
                    todoList.addTask(description, from, by);
                    saveTxt();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Write in this format: \"event [task description] /from [time description] /to [time description]\"");
                    input = scanner.nextLine();
                }
                System.out.println("Got it. I've added this task: \n" + todoList.getLastTask() + "\n" + "Now you have " + todoList.getTaskLength() + " tasks in the list.\n");
                input = scanner.nextLine();
            }
            else {
                try {
                    throw new MaxException("I'm sorry, but I don't know what that means :-(");
                } catch (MaxException e) {
                    System.out.println("invalid input, try adding task in this format: \n");
                    System.out.println("1. todo [description]\n");
                    System.out.println("2. deadline [description] /by [day]\n");
                    System.out.println("3. event [description] /from [day + time] /to [day + time] \n");
                } finally {
                    input = scanner.nextLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        String logo = " __  __          __   __\n"
                + "|  \\/  |   /\\    \\ \\ / /\n" 
                + "| \\  / |  /  \\    \\ V / \n" 
                + "| |\\/| | / /\\ \\    > <  \n" 
                + "| |  | |/ ____ \\  / . \\ \n"
                + "|_|  |_/_/    \\_\\/_/ \\_\\\n";
        System.out.println("Hello from\n" + logo);
        Max chatbot = new Max();
        chatbot.greet();
        chatbot.startConversation();
    }
}
