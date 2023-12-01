package source.model.expressions;

import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.Type;
import source.model.values.Value;

public class ValueExpression implements Expression
{
    Value value;

    public ValueExpression(Value value)
    {
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap)
    {
        return this.value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment)
    {
        return value.getType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.value.toString();
    }
}
