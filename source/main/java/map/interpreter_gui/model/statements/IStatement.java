package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.Type;

public interface IStatement
{
    ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException;

    IStatement deepCopy();

    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException;
}