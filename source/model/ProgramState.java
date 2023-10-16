package source.model;

import java.util.Dictionary;

import source.model.statements.IStatement;
import source.model.structures.List;
import source.model.structures.Stack;
import source.model.values.Value;

public class ProgramState 
{
    private Stack<IStatement> executionStack;
    private Dictionary<String, Value> symbolTabel;
    private List<Value> output;
    private IStatement originalProgram;

    public ProgramState(Stack<IStatement> stack, Dictionary<String, Value> symbolTable, List<Value> output, IStatement program)
    {
        this.executionStack = stack;
        this.symbolTabel = symbolTable;
        this.output = output;
        // this.originalProgram = program;
        // We need to make a deepcopy of this. 

        this.executionStack.push(program);  
    }

    public Stack<IStatement> getExecutionStack()
    {
        return this.executionStack;
    }

    public Dictionary<String, Value> getSymbolTable()
    {
        return this.symbolTabel;
    }

    public List<Value> getOutput()
    {
        return this.output;
    }

    public IStatement getOriginalProgram()
    {
        return this.originalProgram;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
