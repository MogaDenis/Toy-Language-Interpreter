package map.interpreter_gui.model.values;

import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.types.CharType;

public class CharValue implements Value
{
    private final Character character;

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
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof CharValue charValue))
            return false;

        return this.character.equals(charValue.character);
    }

    @Override
    public int hashCode()
    {
        return this.character.hashCode();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character == charValue.getValue());
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character != charValue.getValue());
    }
    
    @Override
    public BoolValue lessThan(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character < charValue.getValue());
    }
    
    @Override
    public BoolValue lessThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character <= charValue.getValue());
    }
    
    @Override
    public BoolValue greaterThan(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character > charValue.getValue());
    }
    
    @Override
    public BoolValue greaterThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof CharValue charValue))
            return new BoolValue(false);

        return new BoolValue(this.character >= charValue.getValue());
    }
    
    @Override
    public String toString()
    {
        return "'" + this.character + "'";
    }

    @Override
    public Value deepCopy()
    {
        return new CharValue(this.character);
    }
}
