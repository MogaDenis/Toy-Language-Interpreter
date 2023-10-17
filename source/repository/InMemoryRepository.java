package source.repository;

import java.util.Vector;

import source.model.ProgramState;

public class InMemoryRepository implements Repository
{
    private Vector<ProgramState> programStates;

    public InMemoryRepository()
    {
        this.programStates = new Vector<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgram()
    {
        if (this.programStates.isEmpty())
            return null;

        return this.programStates.get(0);
    }
}
