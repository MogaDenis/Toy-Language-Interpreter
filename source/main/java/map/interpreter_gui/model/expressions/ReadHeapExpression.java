package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.ReferenceType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.ReferenceValue;
import map.interpreter_gui.model.values.Value;

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
