package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.values.Value;

public interface Expression
{
    public Value evaluate(IDictionary<String, Value> symbolTable) throws StatementException, ExpressionException, ValueException;
}
