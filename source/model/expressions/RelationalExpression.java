package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.*;
import source.model.values.Value;

public class RelationalExpression implements Expression
{
    private Expression expression1;
    private Expression expression2;
    private String relation;

    public RelationalExpression(Expression exp1, Expression exp2, String relation)
    {
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.relation = relation;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException
    {
        Value firstExpressionValue = this.expression1.evaluate(symbolTable, heap);
        Value secondExpressionValue = this.expression2.evaluate(symbolTable, heap);

        if (!firstExpressionValue.getType().equals(secondExpressionValue.getType()))
            throw new ExpressionException("The given expression have different types.");

        return switch(this.relation)
        {
            case "==" -> firstExpressionValue.equal(secondExpressionValue);
            case "!=" -> firstExpressionValue.notEqual(secondExpressionValue);
            case "<" -> firstExpressionValue.lessThan(secondExpressionValue);
            case "<=" -> firstExpressionValue.lessThanOrEqual(secondExpressionValue);
            case ">" -> firstExpressionValue.greaterThan(secondExpressionValue);
            case ">=" -> firstExpressionValue.greaterThanOrEqual(secondExpressionValue);

            default -> throw new ExpressionException("Invalid relational operator.");
        };
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type firstExpressionType = this.expression1.typecheck(typeEnvironment);
        Type secondExpressionType = this.expression2.typecheck(typeEnvironment);

        if (!firstExpressionType.equals(secondExpressionType))
            throw new TypeException("The given expressions have different types.");

        return new BoolType();

//        if (firstExpressionType instanceof IntType)
//            return new IntType();
//
//        if (firstExpressionType instanceof BoolType)
//            return new BoolType();
//
//        if (firstExpressionType instanceof CharType)
//            return new CharType();
//
//        if (firstExpressionType instanceof ReferenceType)
//            return new ReferenceType(((ReferenceType) firstExpressionType).getInner());
//
//        return new StringType();
    }

    @Override
    public Expression deepCopy()
    {
        return new RelationalExpression(this.expression1.deepCopy(), this.expression2.deepCopy(), this.relation);
    }

    @Override
    public String toString()
    {
        return this.expression1.toString() + " " + this.relation + " " + this.expression2.toString();
    }
}
