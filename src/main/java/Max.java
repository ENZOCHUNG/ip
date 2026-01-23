import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Max {
    private String name = "Max";
    private String seperator = "-------------------------------------------";
    private Scanner scanner = new Scanner(System.in);
    private String input;
    private List<String> list = new ArrayList<>();

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

        while(!input.equalsIgnoreCase("bye")) {
            if (input.equalsIgnoreCase("list")) {
                System.out.println(this.list);
                input = scanner.nextLine();
            }
            if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            }
            System.out.println(seperator);
            System.out.println("added: " + this.input);
            System.out.println(seperator);
            list.add(input);
            input = scanner.nextLine();
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


                        