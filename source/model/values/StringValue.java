package source.model.values;

import source.model.exceptions.ValueException;
import source.model.types.StringType;
import source.model.types.Type;

public class StringValue implements Value
{
    private String string;

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
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof StringValue))
            return new BoolValue(false);

        StringValue stringValue = (StringValue)otherValue;

        return new BoolValue(this.string == stringValue.getValue());
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof StringValue))
            return new BoolValue(false);

        StringValue stringValue = (StringValue)otherValue;

        return new BoolValue(this.string != stringValue.getValue());
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
