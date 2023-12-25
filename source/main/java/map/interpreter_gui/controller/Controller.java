package map.interpreter_gui.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.values.ReferenceValue;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.repository.Repository;


public class Controller
{
    private final Repository repository;
    private ExecutorService executor;
    
    public Controller(Repository repository)
    {
        this.repository = repository;
        this.executor = null;
    }

    public List<ProgramState> getProgramStates() {
        return this.repository.getProgramsList();
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws InterruptedException
    {
        this.executor = Executors.newFixedThreadPool(2);

        programs.forEach(this.repository::logProgramStateExecution);

        List<Callable<ProgramState>> callList = programs.stream()
        .map(program -> (Callable<ProgramState>)(program::oneStep))
        .collect(Collectors.toList());

        List<ProgramState> newProgramsList = this.executor.invokeAll(callList).stream()
        .map(future -> { 
                try 
                {
                    return future.get();
                }
                catch(InterruptedException | ExecutionException e)
                {
                    return null;
                }
        })
        .filter(Objects::nonNull)
        .toList();

        programs.addAll(newProgramsList);

        this.repository.setProgramsList(programs);

        programs.forEach(this.repository::logProgramStateExecution);
    }

    public void allSteps() throws InterruptedException
    {
        List<ProgramState> programsList = this.removeCompletedPrograms(this.repository.getProgramsList());

        while (!programsList.isEmpty())
        {
            // Garbage collecting...
            programsList
            .forEach(program -> program.getHeap().setContent(this.safeGarbageCollector(
                this.getAddressesFromSymbolTable(program.getSymbolTable().getContent().values()),
                program.getHeap())));

            this.oneStepForAllPrograms(programsList);

            programsList = this.removeCompletedPrograms(this.repository.getProgramsList());
        }

        this.executor.shutdownNow();

        this.repository.setProgramsList(programsList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programs)
    {
        return programs.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
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
            .filter(e -> symbolTableAddresses.contains(e.getKey()) || heap.isUsed(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
