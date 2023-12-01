package source.model.types;

import source.model.values.CharValue;
import source.model.values.Value;

public class CharType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new CharValue('\0');
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof CharType;
    }

    @Override
    public Type deepCopy()
    {
        return new CharType();
    }

    @Override
    public String toString()
    {
        return "char";
    }
}
