package source.model.expressions;

import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.structures.IDictionary;
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
    public Value evaluate(IDictionary<String, Value> symbolTable) throws StatementException, ExpressionException, ValueException
    {
        Value firstExpressionValue = this.expression1.evaluate(symbolTable);
        Value secondExpressionValue = this.expression2.evaluate(symbolTable);

        if (firstExpressionValue.getType().equals(secondExpressionValue.getType()) == false)
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
    public String toString()
    {
        return this.expression1.toString() + " " + this.relation + " " + this.expression2.toString();
    }
}
