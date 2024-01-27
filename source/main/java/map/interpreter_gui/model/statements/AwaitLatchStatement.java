package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.ILatchTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.Value;

public class AwaitLatchStatement implements IStatement {
    private final String variable;

    public AwaitLatchStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();
        ILatchTable latchTable = programState.getLatchTable();

        Value variableValue = symbolTable.get(this.variable);
        IntValue intValue = (IntValue) variableValue;

        Integer address = intValue.getValue();

        if (!latchTable.isUsed(address))
            throw new StatementException("There isn't a CountDownLatch defined on the given variable.");

        if (latchTable.get(address) > 0)
            executionStack.push(this); // The current program state must wait for the countdown latch to reach 0.

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitLatchStatement(this.variable);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);

        if (variableType == null)
            throw new TypeException("The given variable was not defined in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable does not have the type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "awaitLatch(" + this.variable + ");\n";
    }
}