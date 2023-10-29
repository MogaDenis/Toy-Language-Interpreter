package source.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import source.model.ProgramState;

public class InMemoryRepository implements Repository, AutoCloseable
{
    private Vector<ProgramState> programStates;
    private PrintWriter logFile;
    private String filePath;

    public InMemoryRepository(ProgramState programState, String filePath) throws IOException
    {
        this.programStates = new Vector<ProgramState>();
        this.programStates.add(programState);
        this.filePath = filePath;
        this.logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true)));
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
        this.logFile.write(string);
        this.logFile.flush();
    }

    @Override
    public void close()
    {
        System.out.println("Closing");
        this.logFile.close();
    }
}
