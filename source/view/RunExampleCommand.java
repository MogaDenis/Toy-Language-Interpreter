package source.view;

import source.controller.Controller;

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
        // try 
        // {
            this.controller.allSteps();
        // }
        // catch (Exception e)
        // {
        //     e.printStackTrace();
        // }
    }
}
