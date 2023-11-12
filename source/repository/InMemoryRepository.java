package source.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import source.model.ProgramState;

public class InMemoryRepository implements Repository
{
    private Vector<ProgramState> programStates;
    private String filePath;

    public InMemoryRepository(ProgramState programState, String filePath)
    {
        this.programStates = new Vector<ProgramState>();
        this.programStates.add(programState);
        this.filePath = filePath;
    }

    @Override
    public ProgramState getCurrentProgram() 
    {
        if (this.programStates.isEmpty())
            return null;

        return this.programStates.get(0);
    }

    @Override
    public String getProgramStateString() 
    {
        return this.programStates.get(0).toString();
    }

    @Override
    public void logProgramStateExecution()
    {
        String string = this.getProgramStateString();
        
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true))))
        {
            printWriter.write(string);
            printWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
