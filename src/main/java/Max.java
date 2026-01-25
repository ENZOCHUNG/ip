import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                    input = scanner.nextLine();
                } catch (NumberFormatException e){
                    System.out.println("Write in this format: \"unmark [number]\"");
                    input = scanner.nextLine();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Number keyed in is out of bound. You have " + todoList.getTaskLength() + " task(s)");
                    input = scanner.nextLine();
                }  
            }
            else {
                todoList.addTask(input);
                System.out.println("added: " + this.input);
                input = scanner.nextLine();
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


                        