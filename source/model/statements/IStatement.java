package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;

public interface IStatement
{
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException;

    public IStatement deepCopyStatement();
}