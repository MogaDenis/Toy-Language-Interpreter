package map.interpreter_gui.model.types;

import map.interpreter_gui.model.values.ReferenceValue;
import map.interpreter_gui.model.values.Value;

public class ReferenceType implements Type
{
    private final Type inner;

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

        if (!(object instanceof ReferenceType referenceType))
            return false;

        return this.inner.equals(referenceType.inner);
    }

    @Override
    public Type deepCopy()
    {
        return new ReferenceType(this.inner.deepCopy());
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
