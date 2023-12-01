package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.BoolType;
import source.model.types.Type;
import source.model.values.Value;
import source.model.values.BoolValue;

public class LogicExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private String operation;

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

        if (!value1.getType().equals(new BoolType()))
            throw new ExpressionException("First operand is not of type BoolType.");

        value2 = expression2.evaluate(symbolTable, heap);

        if (!value2.getType().equals(new BoolType()))
            throw new ExpressionException("Second operand is not of type BoolType.");

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