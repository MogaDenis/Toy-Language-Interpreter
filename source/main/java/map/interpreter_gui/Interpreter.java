package map.interpreter_gui;

import java.util.ArrayList;
import java.util.List;

import map.interpreter_gui.controller.Controller;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.repository.InMemoryRepository;
import map.interpreter_gui.repository.ProgramsRepository;
import map.interpreter_gui.view.ExitCommand;
import map.interpreter_gui.view.RunExampleCommand;
import map.interpreter_gui.view.TextMenu;

public class Interpreter 
{
    public static void main(String[] args)
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
        List<Controller> controllers = programsRepositories.stream().map(Controller::new).toList();

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
