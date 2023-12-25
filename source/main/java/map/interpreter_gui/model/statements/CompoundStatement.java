package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IStack;
import map.interpreter_gui.model.types.Type;

public class CompoundStatement implements IStatement
{
    private final IStatement first;
    private final IStatement second;

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