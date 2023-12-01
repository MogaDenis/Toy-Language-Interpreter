package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.IStack;
import source.model.types.Type;

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

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        return this.second.typecheck(this.first.typecheck(typeEnvironment));
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