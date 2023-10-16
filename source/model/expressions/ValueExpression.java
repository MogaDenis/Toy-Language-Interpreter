package source.model.expressions;

import source.model.structures.IDictionary;
import source.model.values.Value;

public class ValueExpression implements Expression
{
    Value value;

    public Value evaluate(IDictionary<String, Value> symbolTable)
    {
        return this.value;
    }

    public String toString()
    {
        return this.value.toString();
    }
}
