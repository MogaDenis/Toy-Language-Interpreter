package map.interpreter_gui.model.values;

import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.Type;

public class BoolValue implements Value 
{
    private final Boolean truthValue;

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
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof BoolValue boolValue))
            return false;

        return this.truthValue.equals(boolValue.truthValue);
    }

    @Override
    public int hashCode()
    {
        return this.truthValue.hashCode();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof BoolValue boolValue))
            return new BoolValue(false);

        return new BoolValue(this.truthValue == boolValue.getValue());
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof BoolValue boolValue))
            return new BoolValue(false);

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

    @Override
    public Value deepCopy()
    {
        return new BoolValue(this.truthValue);
    }
}
