package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.structures.SymbolTable;
import source.model.structures.IHeap;
import source.model.types.Type;
import source.model.values.Value;

public interface Expression
{
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException;

    public Expression deepCopy();

    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException;
}
