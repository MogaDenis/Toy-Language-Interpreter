package source.model.values;

import source.model.exceptions.ValueException;
import source.model.types.Type;
import source.model.types.ReferenceType;

public class ReferenceValue implements Value
{
    private Integer address;
    private Type locationType;

    public ReferenceValue(Integer address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType()
    {
        return new ReferenceType(this.locationType);
    }

    public Integer getAddress()
    {
        return this.address;
    }

    @Override
    public BoolValue equal(Value otherValue)
    {
        if (!(otherValue instanceof ReferenceValue))
            return new BoolValue(false);

        ReferenceValue referenceValue = (ReferenceValue)otherValue;

        return new BoolValue(this.address == referenceValue.address && this.locationType.equals(referenceValue.locationType));
    }

    @Override
    public BoolValue notEqual(Value otherValue)
    {
        return new BoolValue(!this.equal(otherValue).getValue());
    }

    @Override
    public BoolValue lessThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for ReferenceValue.");
    }

    @Override
    public BoolValue lessThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for ReferenceValue.");
    }

    @Override
    public BoolValue greaterThan(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for ReferenceValue.");
    }

    @Override
    public BoolValue greaterThanOrEqual(Value otherValue) throws ValueException
    {
        throw new ValueException("Relation '<' is undefined for ReferenceValue.");
    }

    @Override
    public Value deepCopy()
    {
        return new ReferenceValue(this.address, this.locationType);
    }

    @Override
    public String toString()
    {
        return "(" + String.valueOf(this.address) + ", " + this.locationType.toString() + ")";
    }
}
