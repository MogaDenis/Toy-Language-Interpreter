package map.interpreter_gui.model.statements.latch_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.latch_table.ILatchTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class NewLatchStatement implements IStatement {
    private final String variable;
    private final Expression expression;

    public NewLatchStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        ILatchTable latchTable = programState.getLatchTable();

        IntValue number = (IntValue)this.expression.evaluate(symbolTable, programState.getHeap());

        Integer address = latchTable.add(number.getValue());

        symbolTable.put(this.variable, new IntValue(address));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);

        if (variableType == null)
            throw new TypeException("The given variable was not defined in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable does not have the type int.");

        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof IntType))
            throw new TypeException("The given expression does not have the type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.variable + ", " + this.expression.toString() + ");\n";
    }
}