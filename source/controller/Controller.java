package source.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import source.model.ProgramState;
import source.model.structures.IHeap;
import source.model.values.ReferenceValue;
import source.model.values.Value;
import source.repository.Repository;


public class Controller 
{
    private Repository repository;
    private ExecutorService executor;
    
    public Controller(Repository repository)
    {
        this.repository = repository;
        this.executor = null;
    }

    public String getProgramStateString()
    {
        return this.repository.getProgramStateString();
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws InterruptedException
    {
        programs.forEach(program -> this.repository.logProgramStateExecution(program));

        List<Callable<ProgramState>> callList = programs.stream()
        .map(program -> (Callable<ProgramState>)( () -> { return program.oneStep();} ))
        .collect(Collectors.toList());

        List<ProgramState> newProgramsList = this.executor.invokeAll(callList).stream()
        .map(future -> { 
                try 
                {
                    return future.get();
                }
                catch(InterruptedException | ExecutionException e)
                {
                    throw new RuntimeException(e);
                }
            })
        .filter(program -> program != null)
        .collect(Collectors.toList());

        programs.addAll(newProgramsList);

        this.repository.setProgramsList(programs);

        programs.forEach(program -> this.repository.logProgramStateExecution(program));
    }

    public void allSteps() throws InterruptedException
    {
        this.executor = Executors.newFixedThreadPool(2);
        
        List<ProgramState> programsList = this.removeCompletedPrograms(this.repository.getProgramsList());

        while (programsList.isEmpty() == false)
        {
            // Garbage collecting...
            programsList
            .forEach(program -> this.safeGarbageCollector(
                this.getAddressesFromSymbolTable(program.getSymbolTable().getContent().values()), 
                program.getHeap()));

            this.oneStepForAllPrograms(programsList);

            programsList = this.removeCompletedPrograms(this.repository.getProgramsList());
        }

        this.executor.shutdownNow();

        this.repository.setProgramsList(programsList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programs)
    {
        return programs.stream().filter(program -> program.isNotCompleted()).collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues)
    {
        return symbolTableValues.stream()
            .filter(v->v instanceof ReferenceValue)
            .map(v->{ ReferenceValue refValue = (ReferenceValue)v; return refValue.getAddress();})
            .collect(Collectors.toList());
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, IHeap heap)
    {
        return heap.getContent().entrySet().stream()
            .filter(e->{ return symbolTableAddresses.contains(e.getKey()) || heap.isUsed(e.getKey()); })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
