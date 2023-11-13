package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.types.ReferenceType;
import source.model.values.ReferenceValue;
import source.model.values.Value;

public class WriteHeapStatement implements IStatement
{
    private String variableName;
    private Expression expression;

    public WriteHeapStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(this.variableName) == false)
            throw new StatementException("The given variable name is undefined.");

        Value variableValue = symbolTable.get(this.variableName);

        if (!(variableValue.getType() instanceof ReferenceType))
            throw new StatementException("The given variable is not of ReferenceType.");

        ReferenceValue referenceValue = (ReferenceValue)variableValue;

        IHeap heap = programState.getHeap();

        if (heap.isUsed(referenceValue.getAddress()) == false)
            throw new StatementException("The address of the reference is not in the heap.");

        Value expressionValue = this.expression.evaluate(symbolTable, heap);

        ReferenceType referenceType = (ReferenceType)referenceValue.getType();
        if (expressionValue.getType().equals(referenceType.getInner()) == false)
            throw new StatementException("The type of the variable and the type of the reference do not match.");

        heap.update(referenceValue.getAddress(), expressionValue);

        return programState;
    }

    @Override
    public IStatement deepCopy()
    {
        return new WriteHeapStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "writeHeap(" + this.variableName + ", " + this.expression.toString() + ")\n";
    }
}
