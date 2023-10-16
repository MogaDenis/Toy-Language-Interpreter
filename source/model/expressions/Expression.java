package source.model.expressions;

import source.model.structures.IDictionary;
import source.model.values.Value;

public interface Expression
{
    public Value evaluate(IDictionary<String, Value> symbolTable) throws Exception;
}
