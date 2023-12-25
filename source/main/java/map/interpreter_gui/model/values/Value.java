package map.interpreter_gui.model.values;

import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.types.Type;

public interface Value
{
    Type getType();

    BoolValue equal(Value otherValue);
    BoolValue notEqual(Value otherValue);
    BoolValue lessThan(Value otherValue) throws ValueException;
    BoolValue lessThanOrEqual(Value otherValue) throws ValueException;
    BoolValue greaterThan(Value otherValue) throws ValueException;
    BoolValue greaterThanOrEqual(Value otherValue) throws ValueException;

    Value deepCopy();
}
