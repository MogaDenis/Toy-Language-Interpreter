package source.model.values;

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

    public String toString()
    {
        return String.valueOf(this.truthValue);
    }

    public Type getType()
    {
        return new BoolType();
    }
}
