package source.model.statements;

import source.model.exceptions.TypeException;
import source.model.expressions.Expression;
import source.model.structures.*;
import source.model.types.BoolType;
import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.types.Type;
import source.model.values.BoolValue;
import source.model.values.Value;

public class IfStatement implements IStatement
{
    private final Expression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IStack<IStatement> executionStack = programState.getExecutionStack();
        IHeap heap = programState.getHeap();

//        if (!this.expression.evaluate(programState.getSymbolTable(), heap).getType().equals(new BoolType()))
//            throw new StatementException("The expression is not a logic expression.");

        Value evaluation = this.expression.evaluate(programState.getSymbolTable(), heap);

        BoolValue evaluationTruthValue = (BoolValue)evaluation;

        if (evaluationTruthValue.getValue())
            executionStack.push(this.thenStatement);
        else
            executionStack.push(this.elseStatement);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof BoolType))
            throw new TypeException("The expression is not a logic expression.");

        this.thenStatement.typecheck(((TypeTable)typeEnvironment).deepCopy());
        this.elseStatement.typecheck(((TypeTable)typeEnvironment).deepCopy());

        return typeEnvironment;
    }
    
    @Override
    public IStatement deepCopy()
    {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override 
    public String toString()
    {
        return "if (" + this.expression.toString() + ") then {\n" + this.thenStatement.toString() + 
                "}\nelse {\n" + this.elseStatement.toString() + "}\n";
    }
}
