package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.structures.*;
import source.model.types.Type;
import source.model.values.Value;

public class ForkStatement implements IStatement
{
    private IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws TypeException
    {
        // Heap, fileTable and output are shared. 
        // symbolTable is copied. 

        IHeap heap = programState.getHeap();
        FileTable fileTable = programState.getFileTable();
        IList<Value> output = programState.getOutput();

        SymbolTable symbolTableDeepCopy = programState.getSymbolTable().deepCopy();

        ProgramState newProgramState = new ProgramState(new Stack<IStatement>(), symbolTableDeepCopy, output, fileTable, heap, statement);

        return newProgramState;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        return this.statement.typecheck(typeEnvironment);
    }

    @Override
    public IStatement deepCopy()
    {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString()
    {
        return "fork(" + this.statement.toString() + ")\n"; 
    }
}
