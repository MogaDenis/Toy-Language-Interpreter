package source.model.types;

import source.model.values.ReferenceValue;
import source.model.values.Value;

public class ReferenceType implements Type
{
    private Type inner;

    public ReferenceType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;

        if (!(object instanceof ReferenceType))
            return false;

        ReferenceType referenceType = (ReferenceType)object;

        return this.inner.equals(referenceType.inner);
    }

    @Override
    public Value defaultValue()
    {
        return new ReferenceValue(0, this.inner);
    }

    @Override
    public String toString()
    {
        return "Reference(" + this.inner.toString() + ")";
    }
}
