package source.model.values;

import source.model.types.Type;
import source.model.types.IntType;

public class IntValue implements Value
{
    private Integer value;

    public IntValue(int value)
    {
        this.value = value;
    }

    public Integer getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return String.valueOf(this.value);
    }

    public Type getType()
    {
        return new IntType();
    }
}
