package source.model.statements;

import source.model.ProgramState;

public class NoOperationStatement implements IStatement
{
    public NoOperationStatement()
    {

    }

    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }

    @Override
    public IStatement deepCopyStatement()
    {
        return new NoOperationStatement();
    }
}
