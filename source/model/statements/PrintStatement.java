package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.expressions.Expression;
import source.model.values.Value;

public class PrintStatement implements IStatement
{
    private Expression expression;

    public PrintStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException
    {
        Value evaluation = this.expression.evaluate(programState.getSymbolTable());
        programState.getOutput().add(evaluation);
        return programState;
    }

    @Override
    public String toString()
    {
        return "print(" + this.expression.toString() + ")";
    }
}
