package map.interpreter_gui.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import map.interpreter_gui.model.ProgramState;

public class InMemoryRepository implements Repository
{
    private List<ProgramState> programStates;
    private final String filePath;

    public InMemoryRepository(ProgramState programState, String filePath)
    {
        this.programStates = new Vector<>();
        this.programStates.add(programState);
        this.filePath = filePath;
    }
    
    @Override
    public String getProgramStateString() 
    {
        return this.programStates.getFirst().toString();
    }

    @Override
    public void logProgramStateExecution(ProgramState program)
    {
        String string = program.toString();
        
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true))))
        {
            printWriter.write(string);
            printWriter.flush();
        }
        catch (IOException ignored)
        {

        }
    }

    @Override
    public Vector<ProgramState> getProgramsList()
    {
        return new Vector<>(this.programStates);
    }

    @Override
    public void setProgramsList(List<ProgramState> programs)
    {
        this.programStates = programs;
    }
}
