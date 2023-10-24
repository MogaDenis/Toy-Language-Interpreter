package source.model.types;

import source.model.values.Value;
import source.model.values.IntValue;

public class IntType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof IntType)
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return "int";
    }
}
