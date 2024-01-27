package map.interpreter_gui.model.statements.repetitive_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.expressions.RelationalExpression;
import map.interpreter_gui.model.expressions.VariableExpression;
import map.interpreter_gui.model.statements.AssignmentStatement;
import map.interpreter_gui.model.statements.CompoundStatement;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.Type;

public class ForStatement implements IStatement {
    private final String counter;
    private final Expression initializer;
    private final Expression condition;
    private final Expression step;
    private final IStatement statement;

    public ForStatement(String var, Expression exp1, Expression exp2, Expression step, IStatement statement) {
        this.counter = var;
        this.initializer = exp1;
        this.condition = exp2;
        this.step = step;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        CompoundStatement newCompoundStatement = new CompoundStatement(
                new AssignmentStatement(this.counter, this.initializer),
                new WhileStatement(new RelationalExpression(new VariableExpression(this.counter), this.condition, "<"),
                        new CompoundStatement(this.statement, new AssignmentStatement(this.counter, this.step)))
        );

        ExecutionStack executionStack = programState.getExecutionStack();

        executionStack.push(newCompoundStatement);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(this.counter, this.initializer.deepCopy(), this.condition.deepCopy(),
                this.step.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type counterType = typeEnvironment.get(this.counter);

        if (counterType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (!(counterType instanceof IntType))
            throw new TypeException("The given counter does not have the type int.");

        Type initializerType = this.initializer.typecheck(typeEnvironment);
        Type conditionType = this.condition.typecheck(typeEnvironment);
        Type stepType = this.step.typecheck(typeEnvironment);

        if (!(initializerType instanceof IntType))
            throw new TypeException("The given initializer does not have the type int.");

        if (!(conditionType instanceof IntType))
            throw new TypeException("The given condition does not have the type int.");

        if (!(stepType instanceof IntType))
            throw new TypeException("The given step does not have the type int.");

        return this.statement.typecheck(typeEnvironment);
    }

    @Override
    public String toString() {
        return "for(" + this.counter + " = " + this.initializer + "; " + this.counter + " < " + this.condition +
                "; " + this.counter + " = " + this.step + ") {\n" + this.statement + "}\n";
    }
}