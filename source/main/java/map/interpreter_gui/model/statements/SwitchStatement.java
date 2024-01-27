package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.expressions.RelationalExpression;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.TypeTable;
import map.interpreter_gui.model.types.Type;

public class SwitchStatement implements IStatement {
    private final Expression condition;
    private final Expression firstCaseExpression;
    private final Expression secondCaseExpression;
    private final IStatement firstCaseStatement;
    private final IStatement secondCaseStatement;
    private final IStatement defaultCaseStatement;

    public SwitchStatement(Expression condition, Expression firstCase,
                           Expression secondCase, IStatement firstStatement, IStatement secondStatement,
                           IStatement defaultStatement) {
        this.condition = condition;
        this.firstCaseExpression = firstCase;
        this.secondCaseExpression = secondCase;
        this.firstCaseStatement = firstStatement;
        this.secondCaseStatement = secondStatement;
        this.defaultCaseStatement = defaultStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();

        IfStatement newIfStatement = new IfStatement(new RelationalExpression(this.condition, this.firstCaseExpression, "=="),
                this.firstCaseStatement, new IfStatement(new RelationalExpression(this.condition, this.secondCaseExpression, "=="),
                this.secondCaseStatement, this.defaultCaseStatement));

        executionStack.push(newIfStatement);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(this.condition.deepCopy(), this.firstCaseExpression.deepCopy(),
                this.secondCaseExpression.deepCopy(), this.firstCaseStatement.deepCopy(),
                this.secondCaseStatement.deepCopy(), this.defaultCaseStatement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        Type conditionType = this.condition.typecheck(typeEnvironment);
        Type firstCaseExpressionType = this.firstCaseExpression.typecheck(typeEnvironment);
        Type secondCaseExpressionType = this.secondCaseExpression.typecheck(typeEnvironment);

        if (!conditionType.getClass().equals(firstCaseExpressionType.getClass()))
            throw new TypeException("Expressions in switch and case have different types.");

        if (!conditionType.getClass().equals(secondCaseExpressionType.getClass()))
            throw new TypeException("Expressions in switch and case have different types.");

        this.firstCaseStatement.typecheck(((TypeTable)typeEnvironment).deepCopy());
        this.secondCaseStatement.typecheck(((TypeTable)typeEnvironment).deepCopy());
        this.defaultCaseStatement.typecheck(((TypeTable)typeEnvironment).deepCopy());

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "switch(" + this.condition + ")\n" + "\t(case (" + this.firstCaseExpression + ") {\n" +
                this.firstCaseStatement + "}\n\t(case (" + this.secondCaseExpression + ") {\n" +
                this.secondCaseStatement + "}\n\t(default {\n" + this.defaultCaseStatement + "}\n";
    }
}