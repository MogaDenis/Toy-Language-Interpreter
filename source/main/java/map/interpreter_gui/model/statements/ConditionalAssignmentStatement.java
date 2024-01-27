package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.Type;

public class ConditionalAssignmentStatement implements IStatement {
    private final String variable;
    private final Expression condition;
    private final Expression trueExpression;
    private final Expression falseExpression;

    public ConditionalAssignmentStatement(String var, Expression exp1, Expression exp2, Expression exp3) {
        this.variable = var;
        this.condition = exp1;
        this.trueExpression = exp2;
        this.falseExpression = exp3;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();

        IfStatement newIfStatement = new IfStatement(
                this.condition,
                new AssignmentStatement(this.variable, this.trueExpression),
                new AssignmentStatement(this.variable, this.falseExpression)
        );

        executionStack.push(newIfStatement);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(this.variable, this.condition.deepCopy(), this.trueExpression.deepCopy(), this.falseExpression.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type conditionType = this.condition.typecheck(typeEnvironment);

        if (!(conditionType instanceof BoolType))
            throw new TypeException("The given condition does not evaluate to bool.");

        Type variableType = typeEnvironment.get(this.variable);
        Type trueExpressionType = this.trueExpression.typecheck(typeEnvironment);
        Type falseExpressionType = this.falseExpression.typecheck(typeEnvironment);

        if (variableType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (variableType.getClass() != trueExpressionType.getClass() || variableType.getClass() != falseExpressionType.getClass())
            throw new TypeException("Types of variable and assigned expression are different");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return this.variable + " = " + this.condition + " ? " + this.trueExpression + " : " + this.falseExpression + "\n";
    }
}