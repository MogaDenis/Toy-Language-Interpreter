package source.model.types;

public class StringType implements Type
{
    @Override
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof StringType)
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return "string";
    }
}
