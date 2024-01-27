package map.interpreter_gui.model.expressions;

import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.Value;

public class MULExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;

    public MULExpression(Expression exp1, Expression exp2) {
        this.expression1 = exp1;
        this.expression2 = exp2;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, IHeap heap) throws StatementException, ExpressionException, ValueException {
        IntValue firstExpressionIntValue = (IntValue) this.expression1.evaluate(symbolTable, heap);
        IntValue secondExpressionIntValue = (IntValue) this.expression2.evaluate(symbolTable, heap);

        Integer firstInt = firstExpressionIntValue.getValue();
        Integer secondInt = secondExpressionIntValue.getValue();

        return new IntValue((firstInt * secondInt) - (firstInt + secondInt));
    }

    @Override
    public Expression deepCopy() {
        return new MULExpression(this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type firstExpressionType = this.expression1.typecheck(typeEnvironment);
        Type secondExpressionType = this.expression2.typecheck(typeEnvironment);

        if (!(firstExpressionType instanceof IntType))
            throw new TypeException("The first expression does not have the type int.");

        if (!(secondExpressionType instanceof IntType))
            throw new TypeException("The first expression does not have the type int.");

        return new IntType();
    }

    @Override
    public String toString() {
        return "MUL(" + this.expression1 + ", " + this.expression2 + ")";
    }
}