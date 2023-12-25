package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.*;
import map.interpreter_gui.model.values.Value;

public class RelationalExpression implements Expression
{
    private final Expression expression1;
    private final Expression expression2;
    private final String relation;

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
