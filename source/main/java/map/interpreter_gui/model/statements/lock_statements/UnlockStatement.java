package map.interpreter_gui.model.statements.lock_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.lock_table.ILockTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class UnlockStatement implements IStatement {
    private final String variable;

    public UnlockStatement(String var) {
        this.variable = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        ILockTable lockTable = programState.getLockTable();

        IntValue intValue = (IntValue)symbolTable.get(this.variable);
        Integer address = intValue.getValue();

        if (!lockTable.isUsed(address))
            throw new StatementException("There hasn't be defined a lock on the given variable.");

        if (lockTable.get(address).equals(programState.getID())) {
            lockTable.update(address, -1); // Release the lock.
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(this.variable);
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
        return "unlock(" + this.variable + ");\n";
    }
}