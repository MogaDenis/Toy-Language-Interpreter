package source.model.expressions;

import source.model.exceptions.OperandTypeException;
import source.model.structures.IDictionary;
import source.model.types.BoolType;
import source.model.values.Value;
import source.model.values.BoolValue;

public class LogicExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private int operation; // 1 - AND, 2 - OR

    public Value evaluate(IDictionary<String, Value> symbolTable) throws Exception
    {
        Value value1, value2;

        value1 = expression1.evaluate(symbolTable);

        if (value1.getType().equals(new BoolType()) == false)
            throw new OperandTypeException("First operand is not a logic expression.");

        value2 = expression2.evaluate(symbolTable);

        if (value2.getType().equals(new BoolType()) == false)
            throw new OperandTypeException("Second operand is not a logic expression.");

        BoolValue boolValue1 = (BoolValue)value1;
        BoolValue boolValue2 = (BoolValue)value2;

        boolean boolean1 = boolValue1.getValue();
        boolean boolean2 = boolValue2.getValue();

        if (this.operation == 1) // AND
            return new BoolValue(boolean1 && boolean2);
        
        // OR
        return new BoolValue(boolean1 || boolean2);
    }

    public String toString()
    {
        String operator = "&&";

        if (this.operation == 2)
            operator = "||";

        return this.expression1.toString() + " " + operator + " " + this.expression2.toString();
    }
}