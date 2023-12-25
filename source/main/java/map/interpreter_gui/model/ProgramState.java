package map.interpreter_gui.model;

import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.*;

public class ProgramState 
{
    private final ExecutionStack executionStack;
    private final SymbolTable symbolTable;
    private final OutputList output;
    private final ReadFileTable readFileTable;
    private final WriteFileTable writeFileTable;
    private final IHeap heap;
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
        this.typeTable = new TypeTable();

        this.originalProgram = program.deepCopy();
        this.originalProgram.typecheck(this.typeTable);

        this.executionStack.push(this.originalProgram);

        this.setID();
    }

    public ProgramState(ExecutionStack stack, SymbolTable symbolTable, OutputList output, ReadFileTable readFileTable, WriteFileTable writeFileTable, IHeap heap, IStatement statement, TypeTable typeTable) throws TypeException
    {
        this.executionStack = stack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.readFileTable = readFileTable;
        this.writeFileTable = writeFileTable;
        this.heap = heap;
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
            this.writeFileTable.toStringKeySet() +"\nHeap:\n" +
            this.heap.toString() +
            "\n#####################";
    }
}
