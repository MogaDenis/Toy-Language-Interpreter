package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.SymbolTable;
import source.model.structures.IHeap;
import source.model.values.Value;

public interface Expression
{
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException;

    public Expression deepCopy();
}
