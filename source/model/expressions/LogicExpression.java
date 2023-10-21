package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.structures.IDictionary;
import source.model.types.BoolType;
import source.model.values.Value;
import source.model.values.BoolValue;

public class LogicExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private int operation; // 1 - AND, 2 - OR

    public LogicExpression(Expression expression1, Expression expression2, int operation)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable) throws ExpressionException, StatementException
    {
        Value value1, value2;

        value1 = expression1.evaluate(symbolTable);

        if (value1.getType().equals(new BoolType()) == false)
            throw new ExpressionException("First operand is not a logic expression.");

        value2 = expression2.evaluate(symbolTable);

        if (value2.getType().equals(new BoolType()) == false)
            throw new ExpressionException("Second operand is not a logic expression.");

        BoolValue boolValue1 = (BoolValue)value1;
        BoolValue boolValue2 = (BoolValue)value2;

        boolean boolean1 = boolValue1.getValue();
        boolean boolean2 = boolValue2.getValue();

        if (this.operation == 1) // AND
            return new BoolValue(boolean1 && boolean2);
        
        // OR
        return new BoolValue(boolean1 || boolean2);
    }

    @Override
    public String toString()
    {
        String operator = "&&";

        if (this.operation == 2)
            operator = "||";

        return this.expression1.toString() + " " + operator + " " + this.expression2.toString();
    }
}