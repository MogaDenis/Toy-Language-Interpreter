package source.model;

import java.util.Vector;

import source.model.exceptions.EmptyStackException;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.statements.IStatement;
import source.model.structures.IList;
import source.model.structures.IStack;
import source.model.structures.Stack;
import source.model.structures.SymbolTable;
import source.model.values.Value;
import source.model.structures.FileTable;
import source.model.structures.Heap;
import source.model.structures.IHeap;
import source.model.structures.List;

public class ProgramState 
{
    private IStack<IStatement> executionStack;
    private SymbolTable symbolTable;
    private IList<Value> output;
    private FileTable fileTable;
    private IHeap heap;
    private IStatement originalProgram;
    private Integer id;
    private static Vector<Integer> usedIDs = new Vector<>();

    private Integer getUnusedID()
    {
        Integer newID = 1;

        while (usedIDs.contains(newID))
            newID++;

        return newID;
    }

    public ProgramState(IStatement program)
    {
        this.executionStack = new Stack<IStatement>();
        this.symbolTable = new SymbolTable();
        this.output = new List<Value>();
        this.fileTable = new FileTable();
        this.heap = new Heap();

        this.originalProgram = program.deepCopy();

        this.executionStack.push(this.originalProgram);

        this.id = this.getUnusedID();
        usedIDs.add(this.id);
    }

    public ProgramState(IStack<IStatement> stack, SymbolTable symbolTable, IList<Value> output, FileTable fileTable, IHeap heap, IStatement statement)
    {
        this.executionStack = stack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = statement.deepCopy();

        this.executionStack.push(this.originalProgram);

        this.id = this.getUnusedID();
        usedIDs.add(this.id);
    }

    public ProgramState oneStep() throws EmptyStackException, StatementException, ExpressionException, ValueException
    {
        IStatement currentStatement = this.executionStack.pop();

        return currentStatement.execute(this);
    }

    public Boolean isNotCompleted()
    {
        return this.executionStack.isEmpty() == false;
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
