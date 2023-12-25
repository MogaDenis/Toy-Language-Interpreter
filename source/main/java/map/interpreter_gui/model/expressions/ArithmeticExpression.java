package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.types.CharType;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.values.CharValue;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.StringValue;

public class ArithmeticExpression implements Expression
{
    private final Expression expression1;
    private final Expression expression2;
    private final Character operation;

    public ArithmeticExpression(Character operation, Expression exp1, Expression exp2)
    {
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws ExpressionException, StatementException, ValueException
    {
        Value firstValue = expression1.evaluate(symbolTable, heap);
        Value secondValue = expression2.evaluate(symbolTable, heap);

        Type firstValueType = firstValue.getType();
        Type secondValueType = secondValue.getType();

        if (firstValueType.equals(secondValueType))
        {
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

        // If the operand types are different, allow only concatenation of chars to strings and
        // addition/subtraction of chars with ints.

        if (firstValueType.equals(new StringType()) && secondValueType.equals(new CharType()))
        {
            StringValue firstStringValue = (StringValue)firstValue;
            CharValue secondCharValue = (CharValue)secondValue;

            return new StringValue(firstStringValue.getValue() + secondCharValue.getValue());
        }

        CharValue firstCharValue = (CharValue)firstValue;
        IntValue secondIntValue = (IntValue)secondValue;

        if (this.operation == '+')
            return new CharValue((char)(firstCharValue.getValue() + secondIntValue.getValue()));

        return new CharValue((char)(firstCharValue.getValue() - secondIntValue.getValue()));
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type firstExpressionType = this.expression1.typecheck(typeEnvironment);
        Type secondExpressionType = this.expression2.typecheck(typeEnvironment);

        // If the expression have the same type, allow only concatenation(+) of strings and all operations with ints.
        if (firstExpressionType.equals(secondExpressionType))
        {
            if (!(firstExpressionType instanceof IntType) && !(firstExpressionType instanceof StringType))
                throw new TypeException("Operation " + this.operation + " is not defined for the given operand types: "
                        + firstExpressionType + ", " + firstExpressionType);

            if ((firstExpressionType instanceof StringType) && this.operation != '+')
                throw new TypeException("Operation " + this.operation + " is not defined for StringType.");

            if (firstExpressionType instanceof StringType)
                return new StringType();

            return new IntType();
        }

        // If the operand types are different, allow only concatenation of chars to strings and
        // addition/subtraction of chars with ints.

        // String + Char
        if ((firstExpressionType instanceof StringType) && (secondExpressionType instanceof CharType))
            return new StringType();

        // Allow only Char +/- Int.
        if (!(firstExpressionType instanceof CharType) || !(secondExpressionType instanceof IntType))
            throw new TypeException("Incompatible operands.");

        if (this.operation != '+' && this.operation != '-')
            throw new TypeException("Operation " + this.operation + " is not defined for the given operands types: "
                    + firstExpressionType + ", " + secondExpressionType);

        return new CharType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ArithmeticExpression(this.operation, this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public String toString()
    {
        return this.expression1.toString() + " " + this.operation + " " + this.expression2.toString();
    }
}
