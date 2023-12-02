package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.TypeException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.IStack;
import source.model.structures.SymbolTable;
import source.model.structures.TypeTable;
import source.model.types.BoolType;
import source.model.types.Type;
import source.model.values.BoolValue;
import source.model.values.Value;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.exceptions.ExpressionException;

public class WhileStatement implements IStatement
{
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IStack<IStatement> executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

//        if (!expressionValue.getType().equals(new BoolType()))
//            throw new StatementException("The expression is not a logic expression.");

        BoolValue expressionTruthValue = (BoolValue)expressionValue;

        if (expressionTruthValue.getValue())
        {
            executionStack.push(this);
            executionStack.push(this.statement);
        }

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof BoolType))
            throw new TypeException("The expression is not a logic expression.");

        return this.statement.typecheck(((TypeTable)typeEnvironment).deepCopy());
    }

    @Override
    public IStatement deepCopy()
    {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString()
    {
        return "while (" + this.expression.toString() + ") {\n" + this.statement.toString() + "}\n";
    }
}
