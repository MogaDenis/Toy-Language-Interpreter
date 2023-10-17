package source.model.statements;

import source.model.expressions.Expression;

import source.model.ProgramState;
// import source.model.exceptions.ExpressionException;
// import source.model.exceptions.StatementException;

public class IfStatement implements IStatement
{
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public IStatement deepCopyStatement()
    {
        return new IfStatement(this.expression, this.thenStatement, this.elseStatement);
    }

    @Override
    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }

    @Override 
    public String toString()
    {
        return "(IF(" + this.expression.toString() + ") THEN(" + this.thenStatement.toString() + 
                ") ELSE(" + this.elseStatement.toString() + "))";
    }
}
