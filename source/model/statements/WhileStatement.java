package source.model.statements;

import source.model.ProgramState;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.IStack;
import source.model.types.BoolType;
import source.model.values.BoolValue;
import source.model.values.Value;
import source.model.exceptions.StatementException;
import source.model.exceptions.ExpressionException;

public class WhileStatement implements IStatement
{
    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException
    {
        IStack<IStatement> executionStack = programState.getExecutionStack();
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable);

        if (expressionValue.getType().equals(new BoolType()) == false)
            throw new StatementException("The expression is not a logic expression.");

        BoolValue expressionTruthValue = (BoolValue)expressionValue;

        if (expressionTruthValue.getValue() == true)
        {
            executionStack.push(this);
            executionStack.push(this.statement);
        }

        return programState;
    }

    public IStatement deepCopyStatement()
    {
        return new WhileStatement(this.expression, this.statement);
    }

    @Override
    public String toString()
    {
        return "while (" + this.expression.toString() + ") {\n" + this.statement.toString() + "}\n";
    }
}
