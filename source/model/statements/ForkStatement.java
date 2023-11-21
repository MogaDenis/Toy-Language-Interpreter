package source.model.statements;

import source.model.ProgramState;
import source.model.structures.FileTable;
import source.model.structures.IHeap;
import source.model.structures.Stack;
import source.model.structures.SymbolTable;
import source.model.structures.IList;
import source.model.values.Value;

public class ForkStatement implements IStatement
{
    private IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState)
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
