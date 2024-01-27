package map.interpreter_gui.model;

import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.structures.barrier_table.BarrierTable;
import map.interpreter_gui.model.structures.barrier_table.IBarrierTable;
import map.interpreter_gui.model.structures.count_semaphore_table.CountSemaphoreTable;
import map.interpreter_gui.model.structures.count_semaphore_table.ICountSemaphoreTable;
import map.interpreter_gui.model.structures.latch_table.ILatchTable;
import map.interpreter_gui.model.structures.latch_table.LatchTable;
import map.interpreter_gui.model.structures.lock_table.ILockTable;
import map.interpreter_gui.model.structures.lock_table.LockTable;
import map.interpreter_gui.model.structures.toy_semaphore_table.IToySemaphoreTable;
import map.interpreter_gui.model.structures.toy_semaphore_table.ToySemaphoreTable;

public class ProgramState 
{
    private final ExecutionStack executionStack;
    private final SymbolTable symbolTable;
    private final OutputList output;
    private final ReadFileTable readFileTable;
    private final WriteFileTable writeFileTable;
    private final IHeap heap;
    private final IBarrierTable barrierTable;
    private final ILockTable lockTable;
    private final ILatchTable latchTable;
    private final IToySemaphoreTable toySemaphoreTable;
    private final ICountSemaphoreTable countSemaphoreTable;
    private final IStatement originalProgram;
    private Integer id;
    private final TypeTable typeTable;
    public static Integer currentID = 1;
    public void setID()
    {
        synchronized (ProgramState.class)
        {
            this.id = currentID;
            currentID++;
        }
    }

    public ProgramState(IStatement program) throws TypeException
    {
        this.executionStack = new ExecutionStack();
        this.symbolTable = new SymbolTable();
        this.output = new OutputList();
        this.readFileTable = new ReadFileTable();
        this.writeFileTable = new WriteFileTable();
        this.heap = new Heap();
        this.barrierTable = new BarrierTable();
        this.lockTable = new LockTable();
        this.latchTable = new LatchTable();
        this.toySemaphoreTable = new ToySemaphoreTable();
        this.countSemaphoreTable = new CountSemaphoreTable();
        this.typeTable = new TypeTable();

        this.originalProgram = program.deepCopy();
        this.originalProgram.typecheck(this.typeTable);

        this.executionStack.push(this.originalProgram);

        this.setID();
    }

    public ProgramState(ExecutionStack stack, SymbolTable symbolTable, OutputList output, ReadFileTable readFileTable,
                        WriteFileTable writeFileTable, IHeap heap, IBarrierTable barrierTable, ILockTable lockTable,
                        ILatchTable latchTable, IToySemaphoreTable toySemaphoreTable,
                        ICountSemaphoreTable countSemaphoreTable, IStatement statement, TypeTable typeTable) throws TypeException
    {
        this.executionStack = stack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.readFileTable = readFileTable;
        this.writeFileTable = writeFileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        this.toySemaphoreTable = toySemaphoreTable;
        this.countSemaphoreTable = countSemaphoreTable;
        this.originalProgram = statement.deepCopy();
        this.typeTable = typeTable;

        this.originalProgram.typecheck(this.typeTable);

        this.executionStack.push(this.originalProgram);

        this.setID();
    }

    public Integer getID() {
        return this.id;
    }
    
    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException, ValueException, TypeException
    {
        IStatement currentStatement = this.executionStack.pop();

        return currentStatement.execute(this);
    }

    public Boolean isNotCompleted()
    {
        return !this.executionStack.isEmpty();
    }

    public ExecutionStack getExecutionStack()
    {
        return this.executionStack;
    }

    public SymbolTable getSymbolTable()
    {
        return this.symbolTable;
    }

    public OutputList getOutput()
    {
        return this.output;
    }

    public ReadFileTable getReadFileTable()
    {
        return this.readFileTable;
    }

    public WriteFileTable getWriteFileTable()
    {
        return this.writeFileTable;
    }

    public IHeap getHeap()
    {
        return this.heap;
    }

    public IBarrierTable getBarrierTable() {
        return this.barrierTable;
    }

    public ILockTable getLockTable() {
        return this.lockTable;
    }

    public ILatchTable getLatchTable() {
        return this.latchTable;
    }

    public IToySemaphoreTable getToySemaphoreTable() {
        return this.toySemaphoreTable;
    }

    public ICountSemaphoreTable getCountSemaphoreTable() {
        return this.countSemaphoreTable;
    }

    public TypeTable getTypeTable()
    {
        return this.typeTable;
    }

    public IStatement getOriginalProgram()
    {
        return this.originalProgram;
    }

    @Override
    public String toString()
    {
        return "\n#####################\n\n~Program state ID: " + this.id + " ~\nExecution stack:\n" +
            this.executionStack.toString() + "\nSymbol table:\n" +
            this.symbolTable.toString() + "\nOutput list:\n" +
            this.output.toString() + "\nReadFile table:\n" +
            this.readFileTable.toStringKeySet() + "\nWriteFile table:\n" +
            this.writeFileTable.toStringKeySet() + "\nHeap:\n" +
            this.heap.toString() + "\nBarrier table:\n" +
            this.barrierTable.toString() + "\nLatch table:\n" +
            this.latchTable.toString() + "\nToySemaphore table:\n" +
            this.toySemaphoreTable + "\nCountSemaphore table:\n" +
            this.countSemaphoreTable +
            "\n#####################";
    }

    public String toStringCode() {
        return this.originalProgram.toString();
    }
}
