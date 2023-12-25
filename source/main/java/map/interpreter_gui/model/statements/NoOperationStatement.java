package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.Type;

public class NoOperationStatement implements IStatement
{
    public NoOperationStatement() {}

    @Override
    public ProgramState execute(ProgramState programState)
    {
        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment)
    {
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new NoOperationStatement();
    }

    @Override
    public String toString()
    {
        return "";
    }
}
