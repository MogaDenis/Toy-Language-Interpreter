package source.model.types;

public class BoolType implements Type
{
    @Override
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof BoolType)
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return "bool";
    }
}
