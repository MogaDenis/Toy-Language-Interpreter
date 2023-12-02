package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.types.Type;
import source.model.values.Value;

public class PrintStatement implements IStatement
{
    private final Expression expression;

    public PrintStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public IStatement deepCopy()
    {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        Value evaluation = this.expression.evaluate(programState.getSymbolTable(), programState.getHeap());
        programState.getOutput().add(evaluation);
        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        this.expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return "print(" + this.expression.toString() + ");\n";
    }
}
