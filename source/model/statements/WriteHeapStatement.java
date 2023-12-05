package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.ReferenceType;
import source.model.types.Type;
import source.model.values.ReferenceValue;
import source.model.values.Value;

public class    WriteHeapStatement implements IStatement
{
    private final String variableName;
    private final Expression expression;

    public WriteHeapStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.variableName))
            throw new StatementException("The given variable name is undefined.");

        Value variableValue = symbolTable.get(this.variableName);

        ReferenceValue referenceValue = (ReferenceValue)variableValue;

        IHeap heap = programState.getHeap();

        if (!heap.isUsed(referenceValue.getAddress()))
            throw new StatementException("The address of the reference is not in the heap.");

        Value expressionValue = this.expression.evaluate(symbolTable, heap);

        heap.update(referenceValue.getAddress(), expressionValue);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type variableType = typeEnvironment.get(this.variableName);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(variableType instanceof ReferenceType))
            throw new TypeException("The given variable is not of ReferenceType.");

        if (!expressionType.equals(((ReferenceType)variableType).getInner()))
            throw new TypeException("WriteHeap: Declared type of variable " + this.variableName + " and type of assigned reference expression do not match.");

        return typeEnvironment;
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
