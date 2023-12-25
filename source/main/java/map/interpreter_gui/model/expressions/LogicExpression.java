package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.values.BoolValue;

public class LogicExpression implements Expression
{
    private final Expression expression1;
    private final Expression expression2;
    private final String operation;

    public LogicExpression(Expression expression1, Expression expression2, String operation)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws ExpressionException, StatementException, ValueException
    {
        Value value1, value2;

        value1 = expression1.evaluate(symbolTable, heap);
        value2 = expression2.evaluate(symbolTable, heap);

        BoolValue boolValue1 = (BoolValue)value1;
        BoolValue boolValue2 = (BoolValue)value2;

        boolean boolean1 = boolValue1.getValue();
        boolean boolean2 = boolValue2.getValue();

        if (!this.operation.equals("&&") && !this.operation.equals("||"))
            throw new ExpressionException("Invalid operator.");

        if (this.operation.equals("&&")) // AND
            return new BoolValue(boolean1 && boolean2);
        
        // OR
        return new BoolValue(boolean1 || boolean2);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type firstExpressionType = this.expression1.typecheck(typeEnvironment);
        Type secondExpressionType = this.expression2.typecheck(typeEnvironment);

        if (!(firstExpressionType instanceof BoolType))
            throw new TypeException("First operand is not of type BoolType.");

        if (!(secondExpressionType instanceof BoolType))
            throw new TypeException("Second operand is not of type BoolType.");

        return new BoolType();
    }

    @Override
    public Expression deepCopy()
    {
        return new LogicExpression(this.expression1.deepCopy(), this.expression2.deepCopy(), this.operation);
    }

    @Override
    public String toString()
    {
        return this.expression1.toString() + " " + this.operation + " " + this.expression2.toString();
    }
}