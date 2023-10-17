package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.structures.IDictionary;
import source.model.values.Value;

public class VariableExpression implements Expression
{
    private String id;

    public Value evaluate(IDictionary<String, Value> symbolTable) throws ExpressionException
    {
        if (symbolTable.containsKey(id) == false)
            throw new ExpressionException("Symbol " + id + " is not defined.");

        return symbolTable.get(id);
    }

    // TODO
    // Implement toString...
}
