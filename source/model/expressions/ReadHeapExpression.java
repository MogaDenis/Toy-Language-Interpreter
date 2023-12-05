package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.ReferenceType;
import source.model.types.Type;
import source.model.values.ReferenceValue;
import source.model.values.Value;

public class ReadHeapExpression implements Expression
{
    private final Expression expression;

    public ReadHeapExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException
    {
        Value expressionValue = this.expression.evaluate(symbolTable, heap);
        ReferenceValue referenceValue = (ReferenceValue)expressionValue;

        if (!heap.isUsed(((ReferenceValue)expressionValue).getAddress()))
            throw new StatementException("The given address is not in the heap.");

        return heap.get(referenceValue.getAddress());
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof ReferenceType))
            throw new TypeException("The given expression is not of type ReferenceType.");

        return ((ReferenceType)(expressionType)).getInner();
    }

    @Override
    public Expression deepCopy()
    {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "readHeap(" + this.expression.toString() + ")";
    }
}
