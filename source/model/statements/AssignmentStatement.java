package source.model.statements;

import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.values.Value;
import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.types.Type;

public class AssignmentStatement implements IStatement
{
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IStatement deepCopyStatement()
    {
        return new AssignmentStatement(this.id, this.expression);
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(this.id) == false)
            throw new StatementException("The used variable " + this.id + " was not declared.");

        Value value = this.expression.evaluate(symbolTable);
        Type type = symbolTable.get(this.id).getType();

        if (value.getType().equals(type))
            symbolTable.put(id, value);
        else
            throw new StatementException("Declared type of variable " + this.id + " and type of assigned expression do not match.");

        return programState;
    }

    @Override
    public String toString()
    {
        return this.id + "=" + this.expression.toString() + ";\n";  
    }
}
