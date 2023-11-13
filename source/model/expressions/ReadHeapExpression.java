package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.values.ReferenceValue;
import source.model.values.Value;

public class ReadHeapExpression implements Expression
{
    private Expression expression;

    public ReadHeapExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException
    {
        Value expressionValue = this.expression.evaluate(symbolTable, heap);

        if (!(expressionValue instanceof ReferenceValue))
            throw new StatementException("The given expression does not evaluate to a ReferenceValue.");

        ReferenceValue referenceValue = (ReferenceValue)expressionValue;

        if (heap.isUsed(referenceValue.getAddress()) == false)
            throw new StatementException("The given address is not in the heap.");

        return heap.get(referenceValue.getAddress());
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
