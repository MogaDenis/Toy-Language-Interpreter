package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class SleepStatement implements IStatement {
    private final IntValue number;

    public SleepStatement(IntValue number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();

        if (this.number.getValue() > 0)
            executionStack.push(new SleepStatement(new IntValue(this.number.getValue() - 1)));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement((IntValue)this.number.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "sleep(" + this.number + ");\n";
    }
}