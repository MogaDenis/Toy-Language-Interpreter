package map.interpreter_gui.model.values;

import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.types.IntType;

public class IntValue implements Value
{
    private final Integer value;

    public IntValue(int value)
    {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.value);
    }

    @Override
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof IntValue intValue))
            return false;

        return this.value.equals(intValue.value);
    }

    @Override
    public int hashCode()
    {
        return this.value.hashCode();
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(this.value.equals(intValue.getValue()));
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(!this.value.equals(intValue.getValue()));
    }   

    @Override
    public BoolValue lessThan(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(this.value < intValue.getValue());
    }

    @Override
    public BoolValue lessThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(this.value <= intValue.getValue());
    }

    @Override
    public BoolValue greaterThan(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(this.value > intValue.getValue());
    }

    @Override
    public BoolValue greaterThanOrEqual(Value otherValue)
    {
        if (!(otherValue instanceof IntValue intValue))
            return new BoolValue(false);

        return new BoolValue(this.value >= intValue.getValue());
    }

    @Override
    public Value deepCopy()
    {
        return new IntValue(this.value);
    }
}
