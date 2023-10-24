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
        if (anotherObject instanceof CharType)
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return "char";
    }
}
