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
        return anotherObject instanceof StringType;
    }

    @Override
    public Type deepCopy()
    {
        return new StringType();
    }

    @Override
    public String toString()
    {
        return "string";
    }
}
