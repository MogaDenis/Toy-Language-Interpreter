package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.StatementException;
import source.model.structures.IDictionary;
import source.model.structures.SymbolTable;
import source.model.types.Type;

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
    public IStatement deepCopy()
    {
        return new VariableDeclarationStatement(this.name, this.type);
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(name) == true)
            throw new StatementException("Variable " + this.name + " was already defined.");

        symbolTable.put(this.name, this.type.defaultValue());

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment)
    {
        typeEnvironment.put(this.name, this.type);

        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return this.type.toString() + " " + this.name + ";\n";
    }
}
