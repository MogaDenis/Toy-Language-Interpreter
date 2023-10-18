package source.repository;

import java.util.Vector;

import source.model.ProgramState;

public class InMemoryRepository implements Repository {
    private Vector<ProgramState> programStates;

    public InMemoryRepository(ProgramState programState) {
        this.programStates = new Vector<ProgramState>();
        this.programStates.add(programState);
    }

    @Override
    public ProgramState getCurrentProgram() {
        if (this.programStates.isEmpty())
            return null;

        return this.programStates.get(0);
    }

    public String getProgramStateString() {
        return this.programStates.get(0).toString();
    }
}
