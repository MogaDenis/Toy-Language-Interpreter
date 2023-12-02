package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.*;
import source.model.structures.IDictionary;
import source.model.types.Type;

public interface IStatement
{
    ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException;

    IStatement deepCopy();

    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException;
}