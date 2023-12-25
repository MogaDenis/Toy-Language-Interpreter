package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.Value;

public class VariableExpression implements Expression
{
    private final String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws ExpressionException
    {
        return symbolTable.get(id);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment)
    {
        return typeEnvironment.get(this.id);
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString()
    {
        return this.id;
    }
}
