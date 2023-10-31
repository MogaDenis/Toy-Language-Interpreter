package source.view;

public abstract class Command 
{
    private String key;
    private String description;
    
    public Command(String key, String description)
    {
        this.key = key;
        this.description = description;
    }

    public String getKey()
    {
        return this.key;
    }

    public String getDescription()
    {
        return this.description;
    }

    public abstract void execute();

    @Override
    public String toString()
    {
        return this.key + " -> " + this.description;
    }
}
