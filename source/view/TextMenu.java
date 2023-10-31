package source.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu 
{
    private Map<String, Command> commands;    
    private Scanner scanner;

    public TextMenu()
    {
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void addCommand(Command newCommand)
    {
        this.commands.put(newCommand.getKey(), newCommand);
    }

    private void printMenu()
    {
        for (Command command : this.commands.values())
            System.out.println(command);
    }

    public void show()
    {
        while (true)
        {
            this.printMenu();

            System.out.print(">> ");
            String key = this.scanner.nextLine();

            Command commandToExecute = this.commands.get(key);

            if (commandToExecute == null)
            {
                System.out.println("Invalid option!\n");
                continue;
            }

            commandToExecute.execute();
        }
    }
}
