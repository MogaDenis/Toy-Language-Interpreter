package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.ILockTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class LockStatement implements IStatement {
    private final String variable;

    public LockStatement(String var) {
        this.variable = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();
        ILockTable lockTable = programState.getLockTable();

        IntValue intValue = (IntValue)symbolTable.get(this.variable);
        Integer address = intValue.getValue();

        if (!lockTable.isUsed(address))
            throw new StatementException("There hasn't be defined a lock on the given variable.");

        if (lockTable.get(address) == -1) {
            lockTable.update(address, programState.getID()); // Acquire the lock.
        }
        else {
            executionStack.push(this); // Wait to get hold of the lock.
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new LockStatement(this.variable);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);

        if (variableType == null)
            throw new TypeException("The given variable was not defined in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable is not of type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "lock(" + this.variable + ");\n";
    }
}