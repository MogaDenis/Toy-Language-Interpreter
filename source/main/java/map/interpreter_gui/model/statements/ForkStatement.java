package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.structures.barrier_table.IBarrierTable;
import map.interpreter_gui.model.structures.count_semaphore_table.ICountSemaphoreTable;
import map.interpreter_gui.model.structures.latch_table.ILatchTable;
import map.interpreter_gui.model.structures.lock_table.ILockTable;
import map.interpreter_gui.model.structures.toy_semaphore_table.IToySemaphoreTable;
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
        IBarrierTable barrierTable = programState.getBarrierTable();
        ILockTable lockTable = programState.getLockTable();
        ILatchTable latchTable = programState.getLatchTable();
        IToySemaphoreTable toySemaphoreTable = programState.getToySemaphoreTable();
        ICountSemaphoreTable countSemaphoreTable = programState.getCountSemaphoreTable();
        ReadFileTable readFileTable = programState.getReadFileTable();
        WriteFileTable writeFileTable = programState.getWriteFileTable();
        OutputList output = programState.getOutput();

        SymbolTable symbolTableDeepCopy = programState.getSymbolTable().deepCopy();
        TypeTable typeTable = programState.getTypeTable().deepCopy();

        return new ProgramState(new ExecutionStack(), symbolTableDeepCopy, output, readFileTable, writeFileTable, heap,
                barrierTable, lockTable, latchTable, toySemaphoreTable, countSemaphoreTable, statement, typeTable);
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
