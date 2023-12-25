package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.types.Type;

public class ForkStatement implements IStatement
{
    private final IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws TypeException
    {
        // Heap, fileTable and output are shared. 
        // symbolTable and typeTable are copied.

        IHeap heap = programState.getHeap();
        ReadFileTable readFileTable = programState.getReadFileTable();
        WriteFileTable writeFileTable = programState.getWriteFileTable();
        OutputList output = programState.getOutput();

        SymbolTable symbolTableDeepCopy = programState.getSymbolTable().deepCopy();
        TypeTable typeTable = programState.getTypeTable().deepCopy();

        return new ProgramState(new ExecutionStack(), symbolTableDeepCopy, output, readFileTable, writeFileTable, heap, statement, typeTable);
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
