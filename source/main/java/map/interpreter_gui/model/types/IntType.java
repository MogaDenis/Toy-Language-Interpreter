package map.interpreter_gui.model.types;

import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.values.IntValue;

public class IntType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof IntType;
    }

    @Override
    public Type deepCopy()
    {
        return new IntType();
    }

    @Override
    public String toString()
    {
        return "int";
    }
}
