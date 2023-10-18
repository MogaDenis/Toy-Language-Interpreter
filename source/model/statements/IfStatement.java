package source.model.statements;

import source.model.expressions.Expression;
import source.model.expressions.LogicExpression;
import source.model.structures.IStack;
import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
// import source.model.exceptions.ExpressionException;
// import source.model.exceptions.StatementException;
import source.model.exceptions.StatementException;
import source.model.values.BoolValue;
import source.model.values.Value;

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
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException
    {
        IStack<IStatement> executionStack = programState.getExecutionStack();

        if (this.expression instanceof LogicExpression == false)
            throw new StatementException("The expression is not a logic expression.");

        Value evaluation = this.expression.evaluate(programState.getSymbolTable());

        BoolValue evaluationTruthValue = (BoolValue)evaluation;

        if (evaluationTruthValue.getValue() == true)
            executionStack.push(this.thenStatement);
        else
            executionStack.push(this.elseStatement);

        return programState;
    }

    @Override 
    public String toString()
    {
        return "(IF(" + this.expression.toString() + ") THEN(" + this.thenStatement.toString() + 
                ") ELSE(" + this.elseStatement.toString() + "))";
    }
}
