package map.interpreter_gui.model.statements;

import javafx.util.Pair;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IBarrierTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

import java.util.List;

public class AwaitBarrierStatement implements IStatement {

    private final String variable;

    public AwaitBarrierStatement(String var) {
        this.variable = var;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();
        IntValue intValue = (IntValue)symbolTable.get(this.variable);

        Integer address = intValue.getValue();

        IBarrierTable barrierTable = programState.getBarrierTable();

        if (barrierTable.get(address) == null)
            throw new StatementException("There is no barrier mapped to the given address.");

        Pair<Integer, List<Integer>> pair = barrierTable.get(address);

        Integer threadsToWait = pair.getKey();
        List<Integer> threadsIDs = pair.getValue();

        if (threadsToWait > threadsIDs.size()) {
            barrierTable.addProgramStateIDToList(address, programState.getID());

            executionStack.push(this);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitBarrierStatement(this.variable);
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
        return "await(" + this.variable + ")\n";
    }
}