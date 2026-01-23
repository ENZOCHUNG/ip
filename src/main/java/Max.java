import java.util.Scanner;

public class Max {
    private String name = "Max";
    private String seperator = "-------------------------------------------";
    private Scanner scanner = new Scanner(System.in);
    private String input;

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
            System.out.println(seperator);
            System.out.println(this.input);
            System.out.println(seperator);
            input = scanner.nextLine();
        }
        
        exit();
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


                        