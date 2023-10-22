package source.model.values;

import source.model.exceptions.ValueException;
import source.model.types.BoolType;
import source.model.types.Type;

public class BoolValue implements Value 
{
    private Boolean truthValue;

    public BoolValue(boolean truthValue)
    {
        this.truthValue = truthValue;
    }

    public Boolean getValue()
    {
        return this.truthValue;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.truthValue);
    }

    @Override
    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof BoolValue))
            return new BoolValue(false);

        BoolValue boolValue = (BoolValue)otherValue;


        return new BoolValue(this.truthValue == boolValue.getValue());
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof BoolValue))
            return new BoolValue(false);

        BoolValue boolValue = (BoolValue)otherValue;

        return new BoolValue(this.truthValue != boolValue.getValue());
    }

    @Override
    public BoolValue lessThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for BoolValue.");
    }

    @Override
    public BoolValue lessThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<=' is undefined for BoolValue.");
    }

    @Override
    public BoolValue greaterThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '>' is undefined for BoolValue.");
    }

    @Override
    public BoolValue greaterThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '>=' is undefined for BoolValue.");
    }
}
