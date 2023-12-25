package map.interpreter_gui.model.values;

import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.types.Type;

public class StringValue implements Value
{
    private final String string;

    public StringValue(String value)
    {
        this.string = value;
    }

    @Override
    public Type getType()
    {
        return new StringType();
    }

    public String getValue()
    {
        return this.string;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof StringValue stringValue))
            return false;

        return this.string.equals(stringValue.string);
    }

    @Override
    public int hashCode()
    {
        return this.string.hashCode();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof StringValue stringValue))
            return new BoolValue(false);

        return new BoolValue(this.string.equals(stringValue.getValue()));
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof StringValue stringValue))
            return new BoolValue(false);

        return new BoolValue(!this.string.equals(stringValue.getValue()));
    }

    @Override
    public BoolValue lessThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for StringValue.");
    }

    @Override
    public BoolValue lessThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<=' is undefined for StringValue.");
    }

    @Override
    public BoolValue greaterThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '>' is undefined for StringValue.");
    }

    @Override
    public BoolValue greaterThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '>=' is undefined for StringValue.");
    }

    @Override
    public String toString()
    {
        return "\"" + this.string + "\"";
    }

    @Override
    public Value deepCopy()
    {
        return new StringValue(this.string);
    }
}
