package source.model.statements;

import source.model.ProgramState;
import source.model.structures.IStack;

public class CompoundStatement implements IStatement
{
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement)
    {
        this.first = firstStatement;
        this.second = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState)
    {
        IStack<IStatement> stack = programState.getExecutionStack();

        stack.push(second);
        stack.push(first);

        return programState;
    }
    
    @Override
    public IStatement deepCopy()
    {
        return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
    }
    
    @Override
    public String toString()
    {
        return first.toString() + second.toString();  
    }
}