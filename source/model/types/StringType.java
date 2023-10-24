package source.model.types;

import source.model.values.StringValue;
import source.model.values.Value;

public class StringType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new StringValue("");
    }

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
