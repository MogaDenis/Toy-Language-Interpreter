package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
import source.model.types.Type;
import source.model.types.CharType;
import source.model.types.IntType;
import source.model.types.StringType;
import source.model.values.Value;
import source.model.values.CharValue;
import source.model.values.IntValue;
import source.model.values.StringValue;

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
        Value firstValue = expression1.evaluate(symbolTable);
        Value secondValue = expression2.evaluate(symbolTable);

        Type firstValueType = firstValue.getType();
        Type secondValueType = secondValue.getType();

        /////////////////// If something breaks, remove from here

        if (firstValueType.equals(secondValueType))
        {
            if (firstValueType.equals(new IntType()) && firstValueType.equals(new StringType()))
                throw new ExpressionException("Operation " + this.operation + " is not defined for the given operand types: " 
                                                + firstValueType.toString() + ", " + secondValueType.toString());

            if (firstValueType.equals(new StringType()) && this.operation != '+')
                throw new ExpressionException("Operation " + this.operation + " is not defined for StringType.");

            if (firstValueType.equals(new StringType()))
            {
                StringValue firstStringValue = (StringValue)firstValue;
                StringValue secondStringValue = (StringValue)secondValue;

                return new StringValue(firstStringValue.getValue() + secondStringValue.getValue());
            }

            IntValue firstIntValue = (IntValue)firstValue;
            IntValue secondIntValue = (IntValue)secondValue;

            int number1 = firstIntValue.getValue();
            int number2 = secondIntValue.getValue();

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

        // If the operand types are different, allow only addition/subtraction of chars with ints.

        if (firstValueType.equals(new StringType()) && secondValueType.equals(new CharType()))
        {
            StringValue firstStringValue = (StringValue)firstValue;
            CharValue secondCharValue = (CharValue)secondValue;

            return new StringValue(firstStringValue.getValue() + secondCharValue.getValue());
        }

        if (firstValueType.equals(new CharType()) == false || secondValueType.equals(new IntType()) == false)
            throw new ExpressionException("Incompatible operands.");

        if (this.operation != '+' && this.operation != '-')
            throw new ExpressionException("Operation " + this.operation + " is not defined for the given operands types: " 
                                            + firstValueType.toString() + ", " + secondValueType.toString());

        CharValue firstCharValue = (CharValue)firstValue;
        IntValue secondIntValue = (IntValue)secondValue;

        if (this.operation == '+')
            return new CharValue((char)(firstCharValue.getValue() + secondIntValue.getValue()));

        return new CharValue((char)(firstCharValue.getValue() - secondIntValue.getValue()));

        /////////////////// to here.

        // if (firstValue.getType().equals(new IntType()) == false)
        //     throw new ExpressionException("First operand is not an integer.");

        // if (secondValue.getType().equals(new IntType()) == false)
        //     throw new ExpressionException("Second operand is not an integer.");

        // IntValue intValue1 = (IntValue)firstValue;
        // IntValue intValue2 = (IntValue)secondValue;

        // int number1 = intValue1.getValue();
        // int number2 = intValue2.getValue();

        // if (this.operation == '+') // Addition
        //     return new IntValue(number1 + number2);
        
        // if (this.operation == '-') // Subtraction
        //     return new IntValue(number1 - number2);

        // if (this.operation == '*') // Multiplication
        //     return new IntValue(number1 * number2);

        // // Modulus and Division left
        // // if the second operand is zero, then the result is undefined.

        // if (number2 == 0)
        //     throw new ExpressionException("Division by zero.");

        // // Modulo
        // if (this.operation == '%') 
        //     return new IntValue(number1 % number2);
            
        // // Division
        // return new IntValue(number1 / number2);
    }

    @Override
    public String toString()
    {
        return this.expression1.toString() + " " + this.operation + " " + this.expression2.toString();
    }
}
