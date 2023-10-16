package source.model.expressions;

import source.model.exceptions.DivisionByZeroException;
import source.model.exceptions.OperandTypeException;
import source.model.structures.IDictionary;
import source.model.types.IntType;
import source.model.values.Value;
import source.model.values.IntValue;

public class ArithmeticExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private Integer operation; // 1 - addition, 2 - subtraction, 3 - multiplication, 4 - division

    public ArithmeticExpression(Expression exp1, Expression exp2, int operation)
    {
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = operation;
    }

    public Value evaluate(IDictionary<String, Value> symbolTable) throws Exception
    {
        Value value1, value2;

        value1 = expression1.evaluate(symbolTable);

        if (value1.getType().equals(new IntType()) == false)
            throw new OperandTypeException("First operand is not an integer.");

        value2 = expression2.evaluate(symbolTable);

        if (value2.getType().equals(new IntType()) == false)
            throw new OperandTypeException("Second operand is not an integer.");

        IntValue intValue1 = (IntValue)value1;
        IntValue intValue2 = (IntValue)value2;

        int number1 = intValue1.getValue();
        int number2 = intValue2.getValue();

        if (this.operation == 1) // Addition
            return new IntValue(number1 + number2);
        
        if (this.operation == 2) // Subtraction
            return new IntValue(number1 - number2);

        if (this.operation == 3) // Multiplication
            return new IntValue(number1 * number2);

        if (this.operation == 4) // Division
            if (number2 == 0)
                throw new DivisionByZeroException("Division by zero.");

        return new IntValue(number1 / number2);
    }

    public String toString()
    {
        String operator = "+";
    
        if (this.operation == 2)
            operator = "-";
        else if (this.operation == 3)
            operator = "*";
        else if (this.operation == 4)
            operator = "/";

        return this.expression1.toString() + " " + operator + " " + this.expression2.toString();
    }
}
