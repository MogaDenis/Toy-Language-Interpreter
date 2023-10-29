package source.model;

import java.io.BufferedReader;

import source.model.statements.IStatement;
import source.model.structures.IList;
import source.model.structures.IStack;
import source.model.structures.Stack;
import source.model.values.StringValue;
import source.model.values.Value;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.List;

public class ProgramState 
{
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTabel;
    private IList<Value> output;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;

    public ProgramState(IStatement program)
    {
        this.executionStack = new Stack<IStatement>();
        this.symbolTabel = new Dictionary<String, Value>();
        this.output = new List<Value>();
        this.fileTable = new Dictionary<>();

        this.originalProgram = program.deepCopyStatement();

        this.executionStack.push(program);
    }

    public IStack<IStatement> getExecutionStack()
    {
        return this.executionStack;
    }

    public IDictionary<String, Value> getSymbolTable()
    {
        return this.symbolTabel;
    }

    public IList<Value> getOutput()
    {
        return this.output;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }

    public IStatement getOriginalProgram()
    {
        return this.originalProgram;
    }

    @Override
    public String toString()
    {
        return "\n~Current program state~\nExecution stack:\n" + this.executionStack.toString() + "\nSymbol table:\n" + 
            this.symbolTabel.toString() + "\nOutput list:\n" + this.output.toString() + "\nFile table:\n" + this.fileTable.toString();
    }
}
