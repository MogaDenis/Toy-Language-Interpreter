package map.interpreter_gui.model.types;

import map.interpreter_gui.model.values.Value;

public interface Type 
{
    Value defaultValue();

    Type deepCopy();
}
