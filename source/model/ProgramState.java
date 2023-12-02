package source.model;

import java.util.Vector;

import source.model.exceptions.*;
import source.model.statements.IStatement;
import source.model.structures.*;
import source.model.values.Value;

public class ProgramState 
{
    private final IStack<IStatement> executionStack;
    private final SymbolTable symbolTable;
    private final IList<Value> output;
    private final FileTable fileTable;
    private final IHeap heap;
    private final IStatement originalProgram;
    private final Integer id;
    private static final Vector<Integer> usedIDs = new Vector<>();

    private final TypeTable typeTable;

    private Integer getUnusedID()
    {
        Integer newID = 1;

        while (usedIDs.contains(newID))
            newID++;

        return newID;
    }

    public ProgramState(IStatement program) throws TypeException
    {
        this.executionStack = new Stack<>();
        this.symbolTable = new SymbolTable();
        this.output = new List<>();
        this.fileTable = new FileTable();
        this.heap = new Heap();
        this.typeTable = new TypeTable();

        this.originalProgram = program.deepCopy();
        this.originalProgram.typecheck(this.typeTable);

        this.executionStack.push(this.originalProgram);

        this.id = this.getUnusedID();
        usedIDs.add(this.id);
    }

    public ProgramState(IStack<IStatement> stack, SymbolTable symbolTable, IList<Value> output, FileTable fileTable, IHeap heap, IStatement statement, TypeTable typeTable) throws TypeException
    {
        this.executionStack = stack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = statement.deepCopy();
        this.typeTable = typeTable;

        this.originalProgram.typecheck(this.typeTable);

        this.executionStack.push(this.originalProgram);

        this.id = this.getUnusedID();
        usedIDs.add(this.id);
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

    public IStack<IStatement> getExecutionStack()
    {
        return this.executionStack;
    }

    public SymbolTable getSymbolTable()
    {
        return this.symbolTable;
    }

    public IList<Value> getOutput()
    {
        return this.output;
    }

    public FileTable getFileTable()
    {
        return this.fileTable;
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
            this.output.toString() + "\nFile table:\n" + 
            this.fileTable.toStringKeySet() + "\nHeap:\n" + 
            this.heap.toString() +
            "\n#####################";
    }
}
