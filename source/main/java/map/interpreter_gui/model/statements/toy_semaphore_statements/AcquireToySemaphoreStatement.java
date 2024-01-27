package map.interpreter_gui.model.statements.toy_semaphore_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.toy_semaphore_table.IToySemaphoreTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

import java.util.List;

public class AcquireToySemaphoreStatement implements IStatement {
    private final String variable;

    public AcquireToySemaphoreStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();
        IToySemaphoreTable toySemaphoreTable = programState.getToySemaphoreTable();

        Integer address = ((IntValue)symbolTable.get(this.variable)).getValue();

        if (!toySemaphoreTable.isUsed(address))
            throw new StatementException("There is no ToySemaphore defined for the given variable.");

        List<Integer> programStateIDs = toySemaphoreTable.getListOfProgramStateIDs(address);

        Integer number1 = toySemaphoreTable.getFirstValue(address);
        Integer number2 = toySemaphoreTable.getSecondValue(address);

        if (number1 - number2 > programStateIDs.size()) {
            if (!programStateIDs.contains(programState.getID())) {
                toySemaphoreTable.addProgramStateIDToList(address, programState.getID());
            }
        }
        else {
            executionStack.push(this);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AcquireToySemaphoreStatement(this.variable);
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
        return "acquireToySemaphore(" + this.variable + ");\n";
    }
}