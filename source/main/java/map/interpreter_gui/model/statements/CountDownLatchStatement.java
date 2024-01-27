package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.ILatchTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.Value;

public class CountDownLatchStatement implements IStatement {
    private final String variable;

    public CountDownLatchStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        ILatchTable latchTable = programState.getLatchTable();

        Value variableValue = symbolTable.get(this.variable);
        IntValue intValue = (IntValue) variableValue;

        Integer address = intValue.getValue();

        if (!latchTable.isUsed(address))
            throw new StatementException("There isn't a CountDownLatch defined on the given variable.");

        if (latchTable.get(address) > 0) {
            latchTable.update(address, latchTable.get(address) - 1);
        }

        programState.getOutput().add(new IntValue(programState.getID()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CountDownLatchStatement(this.variable);
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
        return "countDown(" + this.variable + ");\n";
    }
}