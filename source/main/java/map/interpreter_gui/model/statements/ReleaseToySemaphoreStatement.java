package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IToySemaphoreTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

import java.util.List;

public class ReleaseToySemaphoreStatement implements IStatement {
    private final String variable;

    public ReleaseToySemaphoreStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        IToySemaphoreTable toySemaphoreTable = programState.getToySemaphoreTable();

        Integer address = ((IntValue)symbolTable.get(this.variable)).getValue();

        if (!toySemaphoreTable.isUsed(address))
            throw new StatementException("There is no semaphore defined for the given variable.");

        List<Integer> programStateIDs = toySemaphoreTable.getListOfProgramStateIDs(address);

        if (programStateIDs.contains(programState.getID())) {
            toySemaphoreTable.removeProgramStateIDFromList(address, programState.getID());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReleaseToySemaphoreStatement(this.variable);
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
        return "releaseToySemaphore(" + this.variable + ");\n";
    }
}