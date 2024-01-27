package map.interpreter_gui.model.statements.toy_semaphore_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.toy_semaphore_table.IToySemaphoreTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;

public class NewToySemaphoreStatement implements IStatement {
    private final String variable;
    private final Expression expression1;
    private final Expression expression2;

    public NewToySemaphoreStatement(String var, Expression exp1, Expression exp2) {
        this.variable = var;
        this.expression1 = exp1;
        this.expression2 = exp2;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        SymbolTable symbolTable = programState.getSymbolTable();
        IHeap heap = programState.getHeap();

        Integer number1 = ((IntValue)this.expression1.evaluate(symbolTable, heap)).getValue();
        Integer number2 = ((IntValue)this.expression2.evaluate(symbolTable, heap)).getValue();

        IToySemaphoreTable toySemaphoreTable = programState.getToySemaphoreTable();

        Integer address = toySemaphoreTable.add(number1, number2);

        symbolTable.put(this.variable, new IntValue(address));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewToySemaphoreStatement(this.variable, this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type variableType = typeEnvironment.get(this.variable);
        Type firstExpressionType = this.expression1.typecheck(typeEnvironment);
        Type secondExpressionType = this.expression2.typecheck(typeEnvironment);

        if (variableType == null)
            throw new TypeException("The given variable was not defined in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable does not have the type int.");

        if (!(firstExpressionType instanceof IntType))
            throw new TypeException("The first expression does not have the type int.");

        if (!(secondExpressionType instanceof IntType))
            throw new TypeException("The second expression does not have the type int.");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newToySemaphore(" + this.variable + ", " + this.expression1 + ", " + this.expression2 + ");\n";
    }
}