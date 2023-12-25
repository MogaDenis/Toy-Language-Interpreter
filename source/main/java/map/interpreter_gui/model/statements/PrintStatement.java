package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.Value;

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
