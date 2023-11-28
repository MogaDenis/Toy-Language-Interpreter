package source;

import java.util.ArrayList;
import java.util.List;

import source.controller.Controller;
import source.model.ProgramState;
import source.repository.InMemoryRepository;
import source.repository.ProgramsRepository;
import source.view.ExitCommand;
import source.view.RunExampleCommand;
import source.view.TextMenu;

public class Interpreter 
{
    public static void main(String[] args) throws Exception
    {
        // Get all program states from the repository.
        ProgramsRepository programsRepository = new ProgramsRepository();
        List<ProgramState> programsStates = programsRepository.getProgramsStates();

        // Inject each state into its own repository.
        List<InMemoryRepository> programsRepositories = new ArrayList<>();
        var index = new Object(){ int value = 1; };
        programsStates.forEach(program -> { 
                programsRepositories.add(new InMemoryRepository(program, "log" + index.value + ".txt"));
                index.value++;
        } 
        );

        // Inject each repository into its own controller.
        List<Controller> controllers = programsRepositories.stream().map(repository -> new Controller(repository)).toList();

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand(0, "Exit the application."));

        // Create the commands and add them to the TextMenu.
        index.value = 0;
        controllers.forEach(controller -> {
                String exampleProgramString = programsStates.get(index.value).getOriginalProgram().toString();
                textMenu.addCommand(new RunExampleCommand(index.value + 1, exampleProgramString, controller));
                index.value++;
        });

        textMenu.show();
    }
}
