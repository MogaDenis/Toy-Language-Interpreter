package source.model.types;

public class IntType implements Type
{
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof IntType)
            return true;

        return false;
    }

    public String toString()
    {
        return "int";
    }
}
