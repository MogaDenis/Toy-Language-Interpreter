package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;

public interface IStatement
{
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException;

    public IStatement deepCopyStatement();
}