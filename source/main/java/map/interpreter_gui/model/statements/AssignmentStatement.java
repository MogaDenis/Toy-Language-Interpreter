package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.types.Type;

public class AssignmentStatement implements IStatement
{
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IStatement deepCopy()
    {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.id))
            throw new StatementException("The used variable " + this.id + " was not declared.");

        Value value = this.expression.evaluate(symbolTable, programState.getHeap());

        symbolTable.put(id, value);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type variableType = typeEnvironment.get(this.id);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (variableType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (!variableType.equals(expressionType))
            throw new TypeException("Declared type of variable \" + this.id + \" and type of assigned expression do not match.");

        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return this.id + " = " + this.expression.toString() + ";\n";  
    }
}
