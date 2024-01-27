package map.interpreter_gui.model.statements.count_semaphore_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.structures.count_semaphore_table.ICountSemaphoreTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class NewCountSemaphoreStatement implements IStatement {
    private final String variable;
    private final Expression expression;

    public NewCountSemaphoreStatement(String var, Expression exp) {
        this.variable = var;
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        IHeap heap = programState.getHeap();

        Integer number = ((IntValue)this.expression.evaluate(symbolTable, heap)).getValue();

        ICountSemaphoreTable countSemaphoreTable = programState.getCountSemaphoreTable();

        Integer address = countSemaphoreTable.add(number);

        symbolTable.put(this.variable, new IntValue(address));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewCountSemaphoreStatement(this.variable, this.expression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (variableType == null)
            throw new TypeException("The given variable was not defined in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable does not have the type int.");

        if (!(expressionType instanceof IntType))
            throw new TypeException("The expression does not have the type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newCountSemaphore(" + this.variable + ", " + this.expression + ");\n";
    }
}