package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.StatementException;
import source.model.structures.IDictionary;
import source.model.types.BoolType;
import source.model.types.IntType;
// import source.model.exceptions.ExpressionException;
// import source.model.exceptions.StatementException;
import source.model.types.Type;
import source.model.values.BoolValue;
import source.model.values.IntValue;
import source.model.values.Value;

public class VariableDeclarationStatement implements IStatement
{
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public IStatement deepCopyStatement()
    {
        return new VariableDeclarationStatement(this.name, this.type);
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(name) == true)
            throw new StatementException("Variable " + this.name + " was already defined.");

        Value defaultValue = null;

        if (this.type.equals(new IntType()))
            defaultValue = new IntValue(0);
        else if (this.type.equals(new BoolType()))
            defaultValue = new BoolValue(false);

        symbolTable.put(this.name, defaultValue);

        return programState;
    }

    @Override
    public String toString()
    {
        return this.type.toString() + " " + this.name;
    }
}
