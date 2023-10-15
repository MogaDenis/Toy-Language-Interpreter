package source.model.statements;

import source.model.ProgramState;

public interface IStatement
{
    public ProgramState execute(ProgramState programState) throws Exception;
}