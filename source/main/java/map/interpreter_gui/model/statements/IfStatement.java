package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.BoolValue;
import map.interpreter_gui.model.values.Value;

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
