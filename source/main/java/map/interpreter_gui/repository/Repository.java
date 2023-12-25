package map.interpreter_gui.repository;

import map.interpreter_gui.model.ProgramState;
import java.util.List;
import java.util.Vector;

public interface Repository 
{
    String getProgramStateString();

    void logProgramStateExecution(ProgramState program);

    Vector<ProgramState> getProgramsList();

    void setProgramsList(List<ProgramState> programs);
}
