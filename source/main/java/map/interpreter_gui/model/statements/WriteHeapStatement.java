package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.ReferenceType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.ReferenceValue;
import map.interpreter_gui.model.values.Value;

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
