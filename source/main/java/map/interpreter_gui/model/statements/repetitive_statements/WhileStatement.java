package map.interpreter_gui.model.statements.repetitive_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IStack;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.structures.TypeTable;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.BoolValue;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.exceptions.ExpressionException;

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
