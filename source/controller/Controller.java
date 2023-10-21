package source.controller;

import source.model.exceptions.EmptyStackException;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.ProgramState;
import source.model.statements.IStatement;
import source.repository.Repository;
import source.model.structures.IStack;;

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

    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException
    {
        ProgramState programState = this.repository.getCurrentProgram();

        IStack<IStatement> executionStack = programState.getExecutionStack();
        IStatement currentStatement = executionStack.pop();

        return currentStatement.execute(programState);
    }

    public void allSteps() throws EmptyStackException, StatementException, ExpressionException
    {
        ProgramState programState = this.repository.getCurrentProgram();

        while (programState.getExecutionStack().isEmpty() == false)
            this.oneStep(); 
    }
}
