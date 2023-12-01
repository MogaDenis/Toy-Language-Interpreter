package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.Type;
import source.model.values.Value;

public class VariableExpression implements Expression
{
    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws ExpressionException
    {
        if (!symbolTable.containsKey(id))
            throw new ExpressionException("Symbol " + id + " is not defined.");

        return symbolTable.get(id);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment)
    {
        return typeEnvironment.get(this.id);
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString()
    {
        return this.id;
    }
}
