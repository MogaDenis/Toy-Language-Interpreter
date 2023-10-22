package source.model.values;

import source.model.exceptions.ValueException;
import source.model.types.Type;

public interface Value 
{
    public Type getType();

    public BoolValue equal(Value otherValue);
    public BoolValue notEqual(Value otherValue);
    public BoolValue lessThan(Value otherValue) throws ValueException;
    public BoolValue lessThanOrEqual(Value otherValue) throws ValueException;
    public BoolValue greaterThan(Value otherValue) throws ValueException;
    public BoolValue greaterThanOrEqual(Value otherValue) throws ValueException;
}
