package source.view;

public abstract class Command 
{
    private Integer key;
    private String description;
    
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

    public abstract void execute();

    @Override
    public String toString()
    {
        return this.key + " -> " + this.description;
    }
}
