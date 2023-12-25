package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.Value;

public class ValueExpression implements Expression
{
    Value value;

    public ValueExpression(Value value)
    {
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap)
    {
        return this.value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment)
    {
        return value.getType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.value.toString();
    }
}
