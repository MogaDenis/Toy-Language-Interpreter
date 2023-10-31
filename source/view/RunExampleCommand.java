package source.view;

import source.controller.Controller;

public class RunExampleCommand extends Command
{
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    public void execute()
    {
        try 
        {
            this.controller.allSteps();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
