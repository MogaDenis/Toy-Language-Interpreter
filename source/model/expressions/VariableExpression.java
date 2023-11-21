package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
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
        if (symbolTable.containsKey(id) == false)
            throw new ExpressionException("Symbol " + id + " is not defined.");

        return symbolTable.get(id);
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
