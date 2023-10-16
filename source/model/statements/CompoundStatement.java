package source.model.statements;

import source.model.ProgramState;
import source.model.structures.Stack;

public class CompoundStatement implements IStatement
{
    private IStatement first;
    private IStatement second;

    @Override
    public String toString()
    {
        return "(" + first.toString() + ";" + second.toString() + ")";  
    }

    @Override
    public ProgramState execute(ProgramState programState)
    {
        Stack<IStatement> stack = programState.getExecutionStack();

        stack.push(second);
        stack.push(first);

        return programState;
    }
}