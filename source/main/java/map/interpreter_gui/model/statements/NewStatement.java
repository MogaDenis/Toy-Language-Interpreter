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

public class NewStatement implements IStatement
{
    private final String variableName;
    private final Expression expression;

    public NewStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();
        
        if (!symbolTable.containsKey(this.variableName))
            throw new StatementException("The given variable was not defined.");

        if (!(symbolTable.get(this.variableName).getType() instanceof ReferenceType))
            throw new StatementException("The given variable is not of ReferenceType.");

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

        IHeap heap = programState.getHeap();
        Integer address = heap.allocate(expressionValue);

        symbolTable.put(variableName, new ReferenceValue(address, expressionValue.getType()));

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type variableType = typeEnvironment.get(this.variableName);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!variableType.equals(new ReferenceType(expressionType)))
            throw new TypeException("The type of the expression and the type of the reference do not match.");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new NewStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "new(" + this.variableName + ", " + this.expression.toString() + ")\n";
    }
}
