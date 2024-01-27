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

public class NewLockStatement implements IStatement {

    private final String variable;

    public NewLockStatement(String var) {
        this.variable = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        ILockTable lockTable = programState.getLockTable();

        Integer newFreeLocation = lockTable.add();

        symbolTable.put(this.variable, new IntValue(newFreeLocation));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(this.variable);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);

        if (variableType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable does not have the type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newLock(" + this.variable + ")\n";
    }
}