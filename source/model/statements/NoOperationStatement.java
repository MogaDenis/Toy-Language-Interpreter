package source.model.statements;

import source.model.ProgramState;
import source.model.structures.IDictionary;
import source.model.types.Type;

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
