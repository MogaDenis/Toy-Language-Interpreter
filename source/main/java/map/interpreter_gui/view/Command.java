package map.interpreter_gui.view;

public abstract class Command 
{
    private final Integer key;
    private final String description;
    
    public Command(Integer key, String description)
    {
        this.key = key;
        this.description = description;
    }

    public Integer getKey()
    {
        return this.key;
    }

    public String getDescription()
    {
        return this.description;
    }

    public abstract void execute() throws Exception;

    @Override
    public String toString()
    {
        return this.key + " -> " + this.description;
    }
}
