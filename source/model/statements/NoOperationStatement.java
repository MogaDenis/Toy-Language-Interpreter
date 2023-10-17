package source.model.statements;

import source.model.ProgramState;
// import source.model.exceptions.ExpressionException;
// import source.model.exceptions.StatementException;

public class NoOperationStatement implements IStatement
{
    public ProgramState execute(ProgramState programState)
    {
        return programState;
    }
}
