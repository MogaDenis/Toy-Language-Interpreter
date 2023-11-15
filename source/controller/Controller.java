package source.controller;

import source.model.exceptions.EmptyStackException;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import source.model.ProgramState;
import source.model.statements.IStatement;
import source.repository.Repository;
import source.model.structures.IHeap;
import source.model.structures.IStack;
import source.model.values.ReferenceValue;
import source.model.values.Value;;

public class Controller 
{
    private Repository repository;
    
    public Controller(Repository repository)
    {
        this.repository = repository;
    }

    public String getProgramStateString()
    {
        return this.repository.getProgramStateString();
    }

    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException, ValueException
    {
        ProgramState programState = this.repository.getCurrentProgram();

        IStack<IStatement> executionStack = programState.getExecutionStack();
        IStatement currentStatement = executionStack.pop();

        return currentStatement.execute(programState);
    }

    public void allSteps() throws EmptyStackException, StatementException, ExpressionException, ValueException
    {
        ProgramState programState = this.repository.getCurrentProgram();
        this.repository.logProgramStateExecution();

        while (programState.getExecutionStack().isEmpty() == false)
        {
            this.oneStep(); 
            this.repository.logProgramStateExecution();

            programState.getHeap().setContent(this.safeGarbageCollector(
                this.getAddressesFromSymbolTable(programState.getSymbolTable().getContent().values()), 
                programState.getHeap()));
        }
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
