package source.model.statements;

import source.model.expressions.Expression;
import source.model.structures.IStack;
import source.model.types.BoolType;
import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
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
    public IStatement deepCopy()
    {
        return new IfStatement(this.expression, this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IStack<IStatement> executionStack = programState.getExecutionStack();

        if (this.expression.evaluate(programState.getSymbolTable()).getType().equals(new BoolType()) == false)
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
        return "if (" + this.expression.toString() + ") then {\n" + this.thenStatement.toString() + 
                "}\nelse {\n" + this.elseStatement.toString() + "}\n";
    }
}
