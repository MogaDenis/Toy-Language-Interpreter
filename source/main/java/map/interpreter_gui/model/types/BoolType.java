package map.interpreter_gui.model.types;

import map.interpreter_gui.model.values.BoolValue;
import map.interpreter_gui.model.values.Value;

public class BoolType implements Type
{
    @Override
    public Value defaultValue()
    {
        return new BoolValue(false);
    }
    
    @Override
    public boolean equals(Object anotherObject)
    {
        return anotherObject instanceof BoolType;
    }

    @Override
    public Type deepCopy()
    {
        return new BoolType();
    }

    @Override
    public String toString()
    {
        return "bool";
    }
}
