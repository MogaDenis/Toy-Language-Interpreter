package source.model.statements;

import source.model.ProgramState;
// import source.model.exceptions.ExpressionException;
// import source.model.exceptions.StatementException;
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
    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }

    @Override
    public String toString()
    {
        return this.type.toString() + " " + this.name;
    }
}
