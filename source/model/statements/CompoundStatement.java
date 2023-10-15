package source.model.statements;

import source.model.ProgramState;

public class CompoundStatement implements IStatement
{
    private IStatement first;
    private IStatement second;

    @Override
    public String toString()
    {
        return "(" + first.toString() + ";" + second.toString() + ")";  
    }

    @Override
    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }
}