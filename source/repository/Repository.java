package source.repository;

import source.model.ProgramState;
import java.util.List;
import java.util.Vector;

public interface Repository 
{
    public String getProgramStateString();

    public void logProgramStateExecution(ProgramState program);

    public Vector<ProgramState> getProgramsList();

    public void setProgramsList(List<ProgramState> programs);
}
