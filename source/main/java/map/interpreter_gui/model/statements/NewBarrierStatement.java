package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IBarrierTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.Value;

public class NewBarrierStatement implements IStatement {

    private final String variable;
    private final Expression expression;

    public NewBarrierStatement(String var, Expression exp) {
        this.variable = var;
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        IBarrierTable barrierTable = programState.getBarrierTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

        IntValue number = (IntValue)expressionValue;

        Integer newFreeLocation = barrierTable.add(number.getValue());

        symbolTable.put(this.variable, new IntValue(newFreeLocation));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof IntType))
            throw new TypeException("The given expression does not evaluate to an int.");

        Type variableType = typeEnvironment.get(this.variable);

        if (variableType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable is not an int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newBarrier(" + this.variable + ", " + this.expression.toString() + ")\n";
    }
}