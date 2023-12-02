package source.model.values;

import source.model.exceptions.ValueException;
import source.model.types.Type;

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
