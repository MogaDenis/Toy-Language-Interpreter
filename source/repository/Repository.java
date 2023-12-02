package source.repository;

import source.model.ProgramState;
import java.util.List;
import java.util.Vector;

public interface Repository 
{
    String getProgramStateString();

    void logProgramStateExecution(ProgramState program);

    Vector<ProgramState> getProgramsList();

    void setProgramsList(List<ProgramState> programs);
}
