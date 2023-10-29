package source.repository;

import source.model.ProgramState;

public interface Repository 
{
    public ProgramState getCurrentProgram();

    public String getProgramStateString();

    public void logProgramStateExecution();
}
