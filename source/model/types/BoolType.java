package source.model.types;

public class BoolType implements Type
{
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof BoolType)
            return true;

        return false;
    }

    public String toString()
    {
        return "bool";
    }
}
