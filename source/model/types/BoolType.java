package source.model.types;

import source.model.values.BoolValue;
import source.model.values.Value;

public class BoolType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new BoolValue(false);
    }
    
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
