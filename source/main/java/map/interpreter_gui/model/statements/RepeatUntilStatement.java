package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.BoolValue;
import map.interpreter_gui.model.values.Value;

public class RepeatUntilStatement implements IStatement {
    private final Expression expression;
    private final IStatement statement;

    public RepeatUntilStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());
        BoolValue expressionTruthValue = (BoolValue) expressionValue;

        if (!(expressionTruthValue.getValue())) {
            executionStack.push(this);
            executionStack.push(this.statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof BoolType))
            throw new TypeException("The expression is not a logic expression.");

        return this.statement.typecheck(((TypeTable) typeEnvironment).deepCopy());
    }

    @Override
    public String toString() {
        return "Repeat {\n" + this.statement + "} until (" + this.expression + ");\n";
    }
}