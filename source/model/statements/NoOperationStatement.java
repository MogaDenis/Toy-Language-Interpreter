package source.model.statements;

import source.model.ProgramState;

public class NoOperationStatement implements IStatement
{
    public NoOperationStatement() {}

    @Override
    public ProgramState execute(ProgramState programState)
    {
        return null;
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
