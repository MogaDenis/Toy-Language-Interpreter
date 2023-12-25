package map.interpreter_gui.view;

import map.interpreter_gui.controller.Controller;

public class RunExampleCommand extends Command
{
    private final Controller controller;

    public RunExampleCommand(Integer key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    public void execute() throws Exception
    {
        this.controller.allSteps();
    }
}
