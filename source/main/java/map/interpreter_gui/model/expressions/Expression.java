package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.Value;

public interface Expression
{
    Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException;

    Expression deepCopy();

    Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException;
}
