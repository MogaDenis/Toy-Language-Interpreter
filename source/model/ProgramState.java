package source.model;

import source.model.statements.IStatement;
import source.model.structures.IList;
import source.model.structures.IStack;
import source.model.values.Value;
import source.model.structures.IDictionary;

public class ProgramState 
{
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTabel;
    private IList<Value> output;
    private IStatement originalProgram;

    public ProgramState(IStack<IStatement> stack, IDictionary<String, Value> symbolTable, IList<Value> output, IStatement program)
    {
        this.executionStack = stack;
        this.symbolTabel = symbolTable;
        this.output = output;

        // We need to make a deepcopy of this. 
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

    public IStatement getOriginalProgram()
    {
        return this.originalProgram;
    }

    @Override
    public String toString()
    {
        return "Execution stack:\n" + this.executionStack.toString() + "\nSymbol table:\n" + this.symbolTabel.toString() + 
            "\nOutput:\n" + this.output.toString();
    }
}
