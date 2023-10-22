package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.types.IntType;
import source.model.values.Value;
import source.model.values.IntValue;

public class ArithmeticExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private Character operation;

    public ArithmeticExpression(Character operation, Expression exp1, Expression exp2)
    {
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable) throws ExpressionException, StatementException, ValueException
    {
        Value value1, value2;

        value1 = expression1.evaluate(symbolTable);

        if (value1.getType().equals(new IntType()) == false)
            throw new ExpressionException("First operand is not an integer.");

        value2 = expression2.evaluate(symbolTable);

        if (value2.getType().equals(new IntType()) == false)
            throw new ExpressionException("Second operand is not an integer.");

        IntValue intValue1 = (IntValue)value1;
        IntValue intValue2 = (IntValue)value2;

        int number1 = intValue1.getValue();
        int number2 = intValue2.getValue();

        if (this.operation == '+') // Addition
            return new IntValue(number1 + number2);
        
        if (this.operation == '-') // Subtraction
            return new IntValue(number1 - number2);

        if (this.operation == '*') // Multiplication
            return new IntValue(number1 * number2);

        // Modulus and Division left
        // if the second operand is zero, then the result is undefined.

        if (number2 == 0)
            throw new ExpressionException("Division by zero.");

        // Modulo
        if (this.operation == '%') 
            return new IntValue(number1 % number2);
            
        // Division
        return new IntValue(number1 / number2);
    }

    @Override
    public String toString()
    {
        return this.expression1.toString() + " " + this.operation + " " + this.expression2.toString();
    }
}
