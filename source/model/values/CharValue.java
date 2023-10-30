package source.model.values;

import source.model.types.Type;
import source.model.types.CharType;

public class CharValue implements Value
{
    private Character character;

    public CharValue(Character character)
    {
        this.character = character;
    }

    public Character getValue()
    {
        return this.character;
    }

    @Override
    public Type getType()
    {
        return new CharType();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character == charValue.getValue());
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character != charValue.getValue());
    }
    
    @Override
    public BoolValue lessThan(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character < charValue.getValue());
    }
    
    @Override
    public BoolValue lessThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character <= charValue.getValue());
    }
    
    @Override
    public BoolValue greaterThan(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character > charValue.getValue());
    }
    
    @Override
    public BoolValue greaterThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue))
            return new BoolValue(false);

        CharValue charValue = (CharValue)otherValue;

        return new BoolValue(this.character >= charValue.getValue());
    }
    
    @Override
    public String toString()
    {
        return "'" + String.valueOf(this.character) + "'";
    }

    @Override
    public Value deepCopy()
    {
        return new CharValue(this.character);
    }
}
